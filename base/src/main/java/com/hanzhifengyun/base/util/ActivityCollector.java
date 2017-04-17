package com.hanzhifengyun.base.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;


public class ActivityCollector {
    private ActivityCollector() {

    }

    private static List<Activity> sActivityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        if (!sActivityList.contains(activity)) {
            sActivityList.add(activity);
        }
    }

    public static void removeActivity(Activity activity) {
        sActivityList.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : sActivityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public void exitApp() {
        synchronized (ActivityCollector.class) {
            for (Activity activity : sActivityList) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
