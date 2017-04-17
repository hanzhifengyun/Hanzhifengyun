package com.hanzhifengyun.base.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * AlertDialog
 */

public class AlertDialogUtil {
    public static void showDialog(Activity context, String[] strings, String title,
                                  DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setItems(strings, onClickListener).create().show();
    }
}
