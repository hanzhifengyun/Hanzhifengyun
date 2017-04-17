package com.hanzhifengyun.base.permission;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * 权限Api
 */

public interface IPermissionApi {

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                    @NonNull int[] grantResults,
                                    @NonNull Object... receivers);


    boolean hasPermissions(@NonNull Context context, @NonNull String... perms);


    void requestPermissions(@NonNull Activity activity, @NonNull String rationale,
                            final int requestCode, @NonNull final String... perms);

    void requestPermissions(@NonNull Fragment fragment, @NonNull String rationale,
                            final int requestCode, @NonNull final String... perms);


    void requestPermissions(@NonNull android.app.Fragment fragment, @NonNull String rationale,
                            final int requestCode, @NonNull final String... perms);
}
