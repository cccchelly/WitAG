package com.alex.witAg.taskqueue;

import com.alex.witAg.App;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Administrator on 2018-05-07.
 */

public class TaskExecutor extends Thread {
    private long startTime = 0;
    private long waitTime = 1000*60*4; //等待四分钟
    // 任务队列
    private BlockingQueue<ITask> taskQueue;

    // 窗口是否可用
    private boolean isRunning = true;

    public TaskExecutor(BlockingQueue<ITask> taskQueue) {
        this.taskQueue = taskQueue;
        //setDaemon(true);
    }

    // 下班。
    public void quit() {
        isRunning = false;
        interrupt();
    }

    @Override
    public void run() {
        while (isRunning) { // 如果是上班状态就待着。
            ITask iTask;
            try {
                if (App.isIsWaitTaskFinish()){  //还在等待上一个任务回调标记任务完成（标志位置为false表示完成）
                    Thread.sleep(2000);
                    if (System.currentTimeMillis()-startTime>waitTime){ //如果超过waittime时间还未收到完成结果，标记为完成并且执行下一个任务
                        App.setIsWaitTaskFinish(false);
                    }
                    continue;
                }
                iTask = taskQueue.take(); // 获取下一个任务，没有就等着。
                startTime = System.currentTimeMillis();
            } catch (InterruptedException e) {
                if (!isRunning) {
                    // 发生意外了，是下班状态的话就把窗口关闭。
                    interrupt();
                    break; // 如果执行到break，后面的代码就无效了。
                }
                // 发生意外了，不是下班状态，那么窗口继续等待。
                continue;
            }

            // 执行这个任务
            iTask.run();
        }
    }
}
