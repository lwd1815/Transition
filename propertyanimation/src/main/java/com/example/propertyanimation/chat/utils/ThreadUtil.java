package com.example.propertyanimation.chat.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by huang on 2017/2/24.
 */

public class ThreadUtil {
    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    public static void executeSubThread(Runnable runnable){
        EXECUTOR.execute(runnable);
    }
    public static void executeMainThread(Runnable runnable){
        HANDLER.post(runnable);
    }

}
