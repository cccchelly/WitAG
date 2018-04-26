package com.kongqw.serialportlibrary.thread;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Kongqw on 2017/11/14.
 * 串口消息读取线程
 */

public abstract class SerialPortReadThread extends Thread {

    public abstract void onDataReceived(byte[] bytes);

    private static final String TAG = SerialPortReadThread.class.getSimpleName();
    private InputStream mInputStream;
    private byte[] mReadBuffer;

    public SerialPortReadThread(InputStream inputStream) {
        mInputStream = inputStream;
        mReadBuffer = new byte[1024];
    }

/*    public  byte[] InputStreamTOByte(InputStream in) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] b = new byte[1024];
        while ((len = in.read(b, 0, b.length)) != -1) {
            baos.write(b, 0, len);
        }
        byte[] buffer =  baos.toByteArray();
        return buffer;
    }*/

    @Override
    public void run() {
        super.run();

        while (!isInterrupted()) {
            try {
                if (null == mInputStream) {
                    return;
                }
                byte[] readBytes = new byte[64];
                int size  = mInputStream.read(readBytes);
                if (size>0){
                    onDataReceived(readBytes);
                }
                /*Log.i(TAG, "run: ");
                int size = mInputStream.read(mReadBuffer);
                Log.i(TAG,"==mReadBuffer=="+size);
                if (-1 == size || 0 >= size) {
                    return;
                }
                byte[] readBytes = new byte[size];

                System.arraycopy(mReadBuffer, 0, readBytes, 0, size);

                Log.i(TAG, "run: readBytes = " + new String(readBytes));
                Log.i(TAG, "run: readBytessize = " + mInputStream.read(readBytes));
                onDataReceived(readBytes);*/
                //onDataReceived(in2Byte(mInputStream));

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
    public byte[] in2Byte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray(); //in_b为转换之后的结果
        return in2b;
    }
    @Override
    public synchronized void start() {
        super.start();
    }

    /**
     * 关闭线程 释放资源
     */
    public void release() {
        interrupt();

        if (null != mInputStream) {
            try {
                mInputStream.close();
                mInputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
