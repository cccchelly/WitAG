package com.alex.witAg.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;

import com.alex.witAg.App;
import com.alex.witAg.adapter.DeviceAdapter;
import com.alex.witAg.bean.PicPathsBean;
import com.alex.witAg.ui.test.PlaySurfaceView;
import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.INT_PTR;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_JPEGPARA;
import com.hikvision.netsdk.RealDataCallBack;
import com.hikvision.netsdk.StdDataCallBack;
import com.kongqw.serialportlibrary.Device;
import com.kongqw.serialportlibrary.SerialPortFinder;
import com.kongqw.serialportlibrary.SerialPortManager;
import com.kongqw.serialportlibrary.listener.OnOpenSerialPortListener;
import com.kongqw.serialportlibrary.listener.OnSerialPortDataListener;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;


/**
 * Created by Administrator on 2018-03-28.
 */

public class CaptureTaskUtil implements
        OnOpenSerialPortListener {
    private String TAG = CaptureTaskUtil.class.getName();
    private SerialPortManager mSerialPortManager;
    private ArrayList<Device> mDevices;
    private Device mDevice;
    private DeviceAdapter mDeviceAdapter;
    private boolean isDeviceOpenFlag = false;
    StringBuilder deviceStrBuilder = new StringBuilder(); //电机返回参数处理
    StringBuilder captureStrBuilder = new StringBuilder();//摄像头返回的参数处理

    public static  String from = "fromtask";
    public static String FROM_TASK = "fromtask";
    public static String FROM_Hand = "fromhand";

    private static CaptureTaskUtil captureTaskUtil = null;
    private CaptureTaskUtil(){}
    public static CaptureTaskUtil instance(){
        if (captureTaskUtil==null){
            synchronized (CaptureTaskUtil.class) {
                if (captureTaskUtil==null) {
                    captureTaskUtil = new CaptureTaskUtil();
                }
            }
        }
        return captureTaskUtil;

    }
    public void initDevice(Context context){
        SerialPortFinder serialPortFinder = new SerialPortFinder();
        mDevices = serialPortFinder.getDevices();
        if (mDevices == null || mDevices.size() == 0) return;
        mDevice = mDevices.get(0);
        mSerialPortManager = new SerialPortManager();
        Logger.d("device: ", mDevices);
        mDeviceAdapter = new DeviceAdapter(context, mDevices);
        /*直接打开倒数第二个串口*/
        mSerialPortManager.closeSerialPort();
        Device mDevice = mDeviceAdapter.getItem(mDeviceAdapter.getCount()-2);
        openDevice(mDevice);

    }
    /**
     * @fn initeSdk
     */
    private boolean initeSdk() {
        // init net sdk
        if (!HCNetSDK.getInstance().NET_DVR_Init()) {
            Log.e(TAG, "HCNetSDK init is failed!");
            return false;
        }
        HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, "/mnt/sdcard/sdklog/",
                true);
        return true;
    }


    public boolean openDevice(Device device) {
        // 打开串口
        boolean openSerialPort = mSerialPortManager.setOnOpenSerialPortListener((OnOpenSerialPortListener) this)
                .setOnSerialPortDataListener(new OnSerialPortDataListener() {
                    @Override
                    public void onDataReceived(byte[] bytes) {
                        //String str = TextChangeUtil.ByteToString(bytes);
                        String str = TextChangeUtil.ByteToString(bytes);
                        Log.i(TAG, "onDataReceived [ byte[] ]: " + Arrays.toString(bytes));
                        Log.i(TAG, "onDataReceived [ String ]: " + str);
                        handleCallbackMsg(str);
                    }

                    @Override
                    public void onDataSent(byte[] bytes) {
                        Log.i(TAG, "onDataSent [ byte[] ]: " + Arrays.toString(bytes));
                        Log.i(TAG, "onDataSent [ String ]: " + new String(bytes));
                        final byte[] finalBytes = bytes;
                        Log.i(TAG,String.format("发送\n%s", new String(finalBytes)));
                    }
                })
                .openSerialPort(device.getFile(), 9600);

        Log.i(TAG, "onCreate: openSerialPort = " + openSerialPort);
        return openSerialPort;
    }
    /*处理电机和摄像机返回参数*/
    private void handleCallbackMsg(String str) {
        if (str.startsWith("{")&&str.endsWith("}")){ //{xxxxxxx}
            saveMessage(str);
        }else if (str.startsWith("<")&&str.endsWith(">")){ //<yyyyyy>
            saveCaptureMsg(str);
        }else if (str.startsWith("{")&&str.endsWith(">")){  //{xxxxx}<yyyyyyyy>
            String strDevice = str.substring(0,str.indexOf("}")+1);
            String strCap = str.substring(str.indexOf("<"),str.length());
            saveMessage(strDevice);
            saveCaptureMsg(strCap);
        }else if (str.startsWith("{")&&!str.contains("}")) { //{xxxxx
            if (deviceStrBuilder.length()==0) {
                deviceStrBuilder.append(str);
            }else if(deviceStrBuilder.toString().endsWith("}")){
                String secend = new String(deviceStrBuilder.toString());
                deviceStrBuilder.delete(0,deviceStrBuilder.length());
                deviceStrBuilder.append(str);
                deviceStrBuilder.append(secend);
            }
        }else if (str.startsWith("{")&&str.endsWith("<")){ // {xxxxx}<
            saveMessage(str.substring(0,str.indexOf("}")+1));
            captureStrBuilder.append("<");
        }else if (str.startsWith("{")&&str.contains("}<")){ // {xxxxx}<yyy
            saveMessage(str.substring(0,str.indexOf("}")+1));
            captureStrBuilder.append(str.substring(str.indexOf("<"),str.length()));
        }else if (str.endsWith(">")&&!str.contains("<")){ //   yyyy>
            captureStrBuilder.append(str);
        }else if (str.endsWith(">")&&str.startsWith("}<")){  //    }<yyyy>
            saveCaptureMsg(str.substring(str.indexOf("<"),str.length()));
            deviceStrBuilder.append("}");
        }else if (str.endsWith(">")&&str.contains("}<")){  //  xxxx}<yyyy>
            saveCaptureMsg(str.substring(str.indexOf("<"),str.length()));
            Log.i("====",str.indexOf("}")+"");
            deviceStrBuilder.append(str.substring(0,str.indexOf("}")+1));
        }else if (str.endsWith("}")&&!str.startsWith("{")){    //  xxx}
            deviceStrBuilder.append(str);
        }
        Log.i("==captureStrBuilder==",captureStrBuilder.toString());
        Log.i("==deviceStrBuilder==",deviceStrBuilder.toString());
        //Log.i(TAG, "相机返回状态参数"+ captureStrBuilder.toString());
        if (captureStrBuilder.toString().startsWith("<")&& captureStrBuilder.toString().endsWith(">")){
            saveCaptureMsg(captureStrBuilder.toString());
            captureStrBuilder.delete(0, captureStrBuilder.length());
        }
        //Log.i(TAG, "平板返回状态参数"+ deviceStrBuilder.toString());
        if (deviceStrBuilder.toString().startsWith("{")&& deviceStrBuilder.toString().endsWith("}")) {
            saveMessage(deviceStrBuilder.toString());
            deviceStrBuilder.delete(0, deviceStrBuilder.length());
        }
        //Log.i(TAG,"sta="+ShareUtil.getDeviceStatue()+",err="+ ShareUtil.getDeviceError());
    }
    //保存相机参数
    private void saveCaptureMsg(String string) {
        captureStrBuilder.delete(0, captureStrBuilder.length());
        Log.i("==相机：==",string);
        /*CAMsta:x (摄像头电源控制)       x=0----摄像机电源关闭           x=1----摄像机电源开起
          HIGHsta:x （绿板高度状态）    x=1---5（5个状态高度）
          error:x     x=0----舱门电机和高度调节电机无故障        x=1----舱门电机有故障     x=2----高度调节电机有故障 */
        String camSta = CaptureInfoStrUtil.getInstance().getCapValue(string,1);
        String highSta = CaptureInfoStrUtil.getInstance().getCapValue(string,2);
        String error = CaptureInfoStrUtil.getInstance().getCapValue(string,3);
        ShareUtil.saveCaptureCamSta(camSta);
        ShareUtil.saveCaptureHignSta(highSta);
        ShareUtil.saveCaptureErrorSta(error);
    }
    /*保存电机参数*/
    private void saveMessage(String finalString) {
        deviceStrBuilder.delete(0, deviceStrBuilder.length());
        Log.i("==电机：==",finalString);
        //{batvol:12.5v,sunvol:13.5v,sta:x,error:x}   batvol:12.5v----电池电压12.5v  sunvol:13.5v----太阳能电压13.5v  sta:x-----x=0电机复位状态(诱捕状态)
        //  sta:x-----x=1粘虫绿板正面     sta:x-----x=2粘虫绿板反面   error:x----x=0 电机非故障状态  error:x----x=1 电机故障状态
        String batvol = SerialInforStrUtil.getValue(finalString,1);
        String sunvol = SerialInforStrUtil.getValue(finalString,2);
        String sta = SerialInforStrUtil.getValue(finalString,3);
        String error = SerialInforStrUtil.getValue(finalString,4);
        ShareUtil.saveDeviceStatue(sta);
        ShareUtil.saveDeviceError(error);
        ShareUtil.saveDeviceBatvol(batvol);
        ShareUtil.saveDeviceSunvol(sunvol);
        /*SerialBackMessage message = new SerialBackMessage();
        message.setBatvol(batvol);
        message.setSunvol(sunvol);
        message.setSta(sta);
        message.setError(error);
        EventBus.getDefault().post(message);*/
    }

    /**
     * 发送数据
     */
    public void send(String data) {
        Log.i(TAG,"发送给串口-->"+data);
        if (!isDeviceOpenFlag){
            Log.i(TAG,"请先打开串口");
        }
        if (null == data) {
            return;
        }
        if (TextUtils.isEmpty(data)) {
            // getView().showOpenMsg("请输入要发送的信息");
            Log.i(TAG, "onSend: 发送内容为 null");
            return;
        }
        //命令下发控制程序，必须按照下面的顺序：1.x=1（正面）     2.x=2（反面） ，3.x=0（复位） 以上顺序不能跨序号下发命令
        if (TextUtils.equals(data,SerialInforStrUtil.getRiseStr())){  // 1.x=1（正面）
            if (TextUtils.equals(ShareUtil.getDeviceStatue(),"2")){
                skipSend(SerialInforStrUtil.getResetStr(),SerialInforStrUtil.getRiseStr()); //-->0-->1
            }else if (TextUtils.equals(ShareUtil.getDeviceStatue(),"1")){
                Log.i(TAG,"已经是正面");
            }else {
                sendSure(data);
            }
        }else if (TextUtils.equals(data,SerialInforStrUtil.getDeclineStr())){// 2.x=2（反面）
            if (TextUtils.equals(ShareUtil.getDeviceStatue(),"2")){
                Log.i(TAG,"已经是反面");
            }else if (TextUtils.equals(ShareUtil.getDeviceStatue(),"0")){
                skipSend(SerialInforStrUtil.getRiseStr(),SerialInforStrUtil.getDeclineStr());
            }else {
                sendSure(data);
            }
        }else if (TextUtils.equals(data,SerialInforStrUtil.getResetStr())){ // 3.x=0（复位）
            if (TextUtils.equals(ShareUtil.getDeviceStatue(),"1")){
                skipSend(SerialInforStrUtil.getDeclineStr(),SerialInforStrUtil.getResetStr());
            }else if (TextUtils.equals(ShareUtil.getDeviceStatue(),"0")){
                Log.i(TAG,"已复位");
            }else {
                sendSure(data);
            }
        }
    }
    /* 嵌套发送数据
    * first 首次发送状态
    * secend 二次发送状态
    * */
    public void skipSend(String first,String secend){
        Observable.just(first) //-->0
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        String next = "";
                        if (sendSure(s)){
                            for (int i=1; i<10;i++){    //查询状态是否改变
                                Log.i(TAG,"sta="+ShareUtil.getDeviceStatue()+",s="+s);
                                if (TextUtils.equals(s,"{sta:"+ShareUtil.getDeviceStatue()+"}")){
                                    next = secend;
                                }else {
                                    Thread.sleep(1000);
                                }
                            }
                        }
                        return next;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }
                    @Override
                    public void onNext(String s) {
                        Log.i(TAG,"next="+s);
                        if(!TextUtils.equals("",s)){
                            sendSure(s);
                        }
                    }
                    @Override
                    public void onError(Throwable throwable) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    public boolean sendSure(String data){
        byte[] sendContentBytes = data.getBytes();
        boolean sendBytes = mSerialPortManager.sendBytes(sendContentBytes);
        Log.i(TAG, "onSend: sendBytes = " + sendBytes);
        return sendBytes;
    }


    public void capture(String from) {
        this.from = from;
            if (initeSdk()) { //初始化sdk
                Log.i(TAG,"logId="+ShareUtil.getLoginId()+",chanel="+ShareUtil.getChannel());
                Test_CaptureJpegPicture_new(ShareUtil.getLoginId(), ShareUtil.getChannel());
            }
    }

    /**
     * 截图
     * @param iUserID
     * @param iChan
     */
    public  void Test_CaptureJpegPicture_new(int iUserID, int iChan) {
        NET_DVR_JPEGPARA strJpeg = new NET_DVR_JPEGPARA();
        strJpeg.wPicQuality = ShareUtil.getCaptureQuality();   //图片质量  0-最好   1-较好   2-一般
        strJpeg.wPicSize = 2;
        int iBufferSize = 1024 * 1024;
        byte[] sbuffer = new byte[iBufferSize];
        INT_PTR bytesRerned = new INT_PTR();
        if (!HCNetSDK.getInstance().NET_DVR_CaptureJPEGPicture_NEW(iUserID, iChan, strJpeg, sbuffer, iBufferSize, bytesRerned)) {
            Log.i(TAG, "NET_DVR_CaptureJPEGPicture_NEW!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
        } else {
            String deviceStatue = "";
            if (TextUtils.equals(ShareUtil.getDeviceStatue(),"1")){
                deviceStatue = "-A";
            }else if (TextUtils.equals(ShareUtil.getDeviceStatue(),"2")){
                deviceStatue = "-B";
            }else {
                deviceStatue = "-O";
            }
            String picName = TimeUtils.millis2String(System.currentTimeMillis(),
                    new SimpleDateFormat("yyyyMMddHHmmss")) + deviceStatue + ".jpg";
            //文件保存到内存
            FileUtils.saveContentToSdcard(picName,sbuffer);
            //文件地址保存到数据库
            PicPathsBean data = new PicPathsBean();
            data.setPath(picName);
            data.save();
            File file = FileUtils.getFileFromSdcard(picName);

            if (null!=onCaptureFinishListener){
                if (TextUtils.equals(from,FROM_Hand)) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    Bitmap bitmap = FileUtils.getPicFromBytes(sbuffer, options);
                    onCaptureFinishListener.finish(bitmap, file, picName);
                }
            }
            Log.i(TAG, "NET_DVR_CaptureJPEGPicture_NEW size!" + bytesRerned.iValue);
        }
    }

    public void destoryDevice(){
        if (mSerialPortManager != null) {
            mSerialPortManager.closeSerialPort();
            mSerialPortManager = null;
        }
    }

    @Override
    public void onSuccess(File device) {
        Log.i(TAG,String.format("串口 [%s] 打开成功", device.getPath()));
        isDeviceOpenFlag = true;
    }

    @Override
    public void onFail(File device, Status status) {
        isDeviceOpenFlag = false;
        switch (status) {
            case NO_READ_WRITE_PERMISSION:
                Log.i(TAG,device.getPath() + "--- 没有读写权限");
                break;
            case OPEN_FAIL:
            default:
                Log.i(TAG,device.getPath() + "--- 串口打开失败");
                break;
        }
    }

    //相机是否已打开
    public boolean isCaptureOpen() {
        /*CAMsta:x (摄像头电源控制)    x=0----摄像机电源关闭      x=1----摄像机电源开起  */
        boolean isOpen = false;
        if (TextUtils.equals(ShareUtil.getCaptureCamSta(),"1")){
            isOpen = true;
        }
        return isOpen;
    }
    //持续查询相机是否已打开
    public boolean isCaptureOpenLong() {
        /*CAMsta:x (摄像头电源控制)    x=0----摄像机电源关闭      x=1----摄像机电源开起  */
        boolean isOpen = false;
        for (int i=1; i<5*60;i++){    //查询状态是否改变  若状态未改变休眠一秒继续查询
            if (!TextUtils.equals(ShareUtil.getCaptureCamSta(),"1")){
                Log.i("==isCapOpen==",i+"");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                isOpen = true;
                break;
            }
        }
        return isOpen;
    }

    //相机是否已关闭
    public boolean isCaptureClose(){
        /*CAMsta:x (摄像头电源控制)    x=0----摄像机电源关闭      x=1----摄像机电源开起  */
        boolean isOpen = false;
            if (TextUtils.equals(ShareUtil.getCaptureCamSta(),"0")) {
                isOpen = true;
            }
        return isOpen;
    }
    //相机是否已关闭
    public boolean isCaptureCloseLong(){
        /*CAMsta:x (摄像头电源控制)    x=0----摄像机电源关闭      x=1----摄像机电源开起  */
        boolean isOpen = false;
        for (int i=1; i<5*60;i++){    //查询状态是否改变  若状态未改变休眠一秒继续查询
            if (!TextUtils.equals(ShareUtil.getCaptureCamSta(),"0")){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                isOpen = true;
            }
        }
        return isOpen;
    }

    //发送命令打开相机
    public boolean openCapture(){
        sendSure(SerialInforStrUtil.openCamer());   //开启摄像头
        return isCaptureOpen();
    }

    //发送命令关闭相机
    public boolean clossCapture(){
        sendSure(SerialInforStrUtil.closeCamer());
        return isCaptureClose();
    }

    //登录设备
    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;
    private StdDataCallBack cbf = null;
    private RealDataCallBack rdf = null;
    private SurfaceView m_osurfaceView = null;
    private static PlaySurfaceView[] playView = new PlaySurfaceView[4];

    public void login(){
        try {
            // login on the device
            ShareUtil.saveLoginId(loginDevice());
            if (ShareUtil.getLoginId() < 0) {
                Log.e(TAG, "This device logins failed!");
                return;
            } else {
                Log.e(TAG, "m_iLogID=" + ShareUtil.getLoginId());
            }
            // get instance of exception callback and set
            ExceptionCallBack oexceptionCbf = getExceptiongCbf();
            if (oexceptionCbf == null) {
                Log.e(TAG, "ExceptionCallBack object is failed!");
                return;
            }

            if (!HCNetSDK.getInstance().NET_DVR_SetExceptionCallBack(
                    oexceptionCbf)) {
                Log.e(TAG, "NET_DVR_SetExceptionCallBack is failed!");
                return;
            }

            //                m_oLoginBtn.setText("Logout");
            Log.i(TAG,
                    "Login sucess ****************************1***************************");

        } catch (Exception err) {
            Log.e(TAG, "error: " + err.toString());
        }
    }

    private int loginDevice() {
        int iLogID = -1;
        if (!initeSdk()) {
            return iLogID;
        }
        iLogID = loginNormalDevice();
        return iLogID;
    }

    private int loginNormalDevice() {
        // get instance
        m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
        if (null == m_oNetDvrDeviceInfoV30) {
            Log.e(TAG, "HKNetDvrDeviceInfoV30 new is failed!");
            return -1;
        }
        String strIP = ShareUtil.getIp();//IP地址
        int nPort = ShareUtil.getPort();//端口号
        String strUser = ShareUtil.getUser();//用户名
        String strPsd = ShareUtil.getPass();//密码
        Log.i("==CapLogin==","IP:"+strIP+",port:"+nPort+",user:"+strUser+",pass:"+strPsd);
        // call NET_DVR_Login_v30 to login on, port 8000 as default
        int iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(strIP, nPort,
                strUser, strPsd, m_oNetDvrDeviceInfoV30);
        if (iLogID < 0) {
            Log.e(TAG, "NET_DVR_Login is failed!Err:"
                    + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return -1;
        }
        Log.i(TAG,(m_oNetDvrDeviceInfoV30.byChanNum)+"");
        if (m_oNetDvrDeviceInfoV30.byChanNum > 0) {
            //m_iStartChan = m_oNetDvrDeviceInfoV30.byStartChan;
            ShareUtil.saveChannel(m_oNetDvrDeviceInfoV30.byChanNum);
        } else if (m_oNetDvrDeviceInfoV30.byIPChanNum > 0) {
            //m_iStartChan = m_oNetDvrDeviceInfoV30.byStartDChan;
            ShareUtil.saveChannel(m_oNetDvrDeviceInfoV30.byIPChanNum
                    + m_oNetDvrDeviceInfoV30.byHighDChanNum * 256);
        }

        Log.i(TAG, "NET_DVR_Login is Successful!");
        App.setIsNeedReLogin(false);
        return iLogID;
    }

    private ExceptionCallBack getExceptiongCbf() {
        ExceptionCallBack oExceptionCbf = new ExceptionCallBack() {
            public void fExceptionCallBack(int iType, int iUserID, int iHandle) {
                System.out.println("recv exception, type:" + iType);
            }
        };
        return oExceptionCbf;
    }

    private OnCaptureFinishListener onCaptureFinishListener;
    public void setOnCaptureFinishListener(OnCaptureFinishListener onCaptureFinishListener){
        this.onCaptureFinishListener = onCaptureFinishListener;
    }
    public interface  OnCaptureFinishListener{
        void finish(Bitmap bitmap,File file,String name);
    }
}
