package com.example.content.Base;

import android.app.Application;
import android.content.Context;

/**
 * Created by 佳南 on 2017/10/22.
 */

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext() {
        return context;
    }
}
