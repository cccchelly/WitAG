package com.alex.witAg.taskqueue;

import android.util.Log;

import com.alex.witAg.App;
import com.alex.witAg.utils.CaptureTaskUtil;


/**
 * Created by Administrator on 2018-05-07.
 */

public  class SeralTask implements ITask{
    private String send;

    public SeralTask(String send){
        this.send = send;
    }

    @Override
    public void run() {
        App.setIsWaitTaskFinish(true);
        Log.i("==task==",send);
        if (!CaptureTaskUtil.instance().send(send)){ //发送消息到串口，如果发送失败则置反标志位认为本次任务完成
            App.setIsWaitTaskFinish(false);
        }
    }

}