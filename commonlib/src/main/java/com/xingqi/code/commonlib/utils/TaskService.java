package com.xingqi.code.commonlib.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskService {

    /**
     * 售票员相当于CPU对应的内核和超线程，线程池类似火车站售票窗口，
     * 假设最大窗口是10，当前只开放5个窗口，
     * 当5个窗口都沾满的时候，后面的人进行排队，当排队已满的时候，
     * 不得不再开放几个售票窗口，进行售票，如果售票窗口已满，排队已满，
     * 售票厅无法容纳，将拒绝进入售票厅。
     */

    //固定窗口，也是最大窗口数，无限排队没有限制，适用于固定任务数的场景。
    private static ExecutorService fixedService;

    //无限多的窗口数，没有排队队列，如果没有窗口空闲，则新开窗口售票,适用于执行时间短的任务。
    private static ExecutorService cachedService;

    //只有一个窗口
    private static ExecutorService singleService;


    private  static Handler mHandler = null;

    private TaskService(){
        int cpuCount = Runtime.getRuntime().availableProcessors();
        //IO密集型：2*NCPU，CPU密集型：NCPU+1
        fixedService = Executors.newFixedThreadPool(cpuCount*2);
        cachedService = Executors.newCachedThreadPool();
        singleService = Executors.newSingleThreadExecutor();
        mHandler = new Handler(Looper.getMainLooper());

    }

    public static class Holder{
        private static TaskService instance = new TaskService();
    }

    private static TaskService taskService;

    public static TaskService getInstance(){
       taskService = Holder.instance;
        return taskService;
    }

    public <T> Future<T> executeOnFixService(Callable<T> callable){
        return fixedService.submit(callable);
    }
    public void executeOnFixService(Runnable task){
        fixedService.execute(task);
    }

    public <T> Future<T> executeOnCachedService(Callable<T> callable){
        return cachedService.submit(callable);
    }
    public void executeOnCachedService(Runnable task){
        cachedService.execute(task);
    }

    public <T> Future<T> executeOnSingleService(Callable<T> callable){
        return singleService.submit(callable);
    }
    public void executeOnSingleService(Runnable task){
        singleService.execute(task);
    }

    public void postTaskInMain(Runnable runnable){
        if(runnable!=null){
            mHandler.post(runnable);
        }
    }
    public void shutDownAll(){
        if(!fixedService.isShutdown()){
            fixedService.shutdownNow();
        }
        if(!cachedService.isShutdown()){
            cachedService.shutdownNow();
        }
        if(!singleService.isShutdown()){
            singleService.shutdownNow();
        }
    }
}
