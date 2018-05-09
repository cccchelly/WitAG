package com.alex.witAg.taskqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by Administrator on 2018-05-07.
 */

public class TaskQueue {

    // 任务队列
    private  BlockingQueue<ITask> mTaskQueue;
    // 好多窗口。
    private TaskExecutor[] mTaskExecutors;

    private static TaskQueue taskQueue;

    // 在开发者new队列的时候，要指定窗口数量。此处用单例默认只提供一个窗口
    private TaskQueue(){
        mTaskQueue = new LinkedBlockingQueue<>();
        mTaskExecutors = new TaskExecutor[1];
        start();
    }
    public static TaskQueue getInstance() {
        if (taskQueue==null){
            synchronized (TaskQueue.class){
                if (taskQueue==null){
                    taskQueue = new TaskQueue();
                }
            }
        }
        return taskQueue;
    }

    // 开始上班。
    public void start() {
        stop();
        // 把各个窗口都打开，让窗口开始上班。
        for (int i = 0; i < mTaskExecutors.length; i++) {
            mTaskExecutors[i] = new TaskExecutor(mTaskQueue);
            mTaskExecutors[i].start();
        }
    }

    // 统一各个窗口下班。
    public void stop() {
        if (mTaskExecutors != null)
            for (TaskExecutor taskExecutor : mTaskExecutors) {
                if (taskExecutor != null) taskExecutor.quit();
            }
    }

    // 添加任务
    public <T extends ITask> int add(T task) {
        if (!mTaskQueue.contains(task)) {
            mTaskQueue.add(task);
        }
        // 返回任务数量
        return mTaskQueue.size();
    }
}
