package com.fengyun.hanzhifengyun.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by fengyun on 2015/10/20.
 */
public class ToastUtil {
    private static Toast mToast;

    public static void showToast(Context context, CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }
}