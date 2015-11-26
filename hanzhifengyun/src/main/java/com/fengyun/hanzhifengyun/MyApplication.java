package com.fengyun.hanzhifengyun;

import android.app.Application;
import android.content.Context;

/**
 * Created by fengyun on 2015/10/26.
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


    public static Context getContext(){
        return context;
    }
}
