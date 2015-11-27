package com.fengyun.hanzhifengyun.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by fengyun on 2015/11/27.
 */
public class LockPatternUtil {

    private LockPatternUtil(){

    }

    /**
     * 查看是否设置锁屏密码
     */
    public static boolean isLockPatternSetting(Context context){
        return !TextUtils.isEmpty(getLockPatternPassword(context));
    }

    /**
     * 关闭图案密码功能
     */
    public static void closeLockPattern(Context context){
        saveLockPatternPassword(context, "");
    }



    /**
     * 获取图案密码
     */
    public static String getLockPatternPassword(Context context){
        SharedPreferences sp = context.getSharedPreferences("LockPattern", Context.MODE_PRIVATE);
        return sp.getString("password", null);
    }

    /**
     * 保存图案密码
     */
    public static void saveLockPatternPassword(Context context, String password){
        SharedPreferences sp = context.getSharedPreferences("LockPattern",
                Context.MODE_PRIVATE);
        sp.edit().putString("password", password).commit();
}
}
