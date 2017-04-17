package com.hanzhifengyun.base.permission.easypermissions;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.hanzhifengyun.base.permission.IPermissionApi;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * https://github.com/googlesamples/easypermissions
 * 第三方权限框架
 */

public class EasyPermissionsApi implements IPermissionApi {

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults,
                                           @NonNull Object... receivers) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, receivers);
    }

    @Override
    public boolean hasPermissions(@NonNull Context context, @NonNull String... perms) {
        return EasyPermissions.hasPermissions(context, perms);
    }

    @Override
    public void requestPermissions(@NonNull Activity activity, @NonNull String rationale, int requestCode, @NonNull String... perms) {
        EasyPermissions.requestPermissions(activity, rationale, requestCode, perms);
    }

    @Override
    public void requestPermissions(@NonNull Fragment fragment, @NonNull String rationale, int requestCode, @NonNull String... perms) {
        EasyPermissions.requestPermissions(fragment, rationale, requestCode, perms);
    }

    @Override
    public void requestPermissions(@NonNull android.app.Fragment fragment, @NonNull String rationale, int requestCode, @NonNull String... perms) {
        EasyPermissions.requestPermissions(fragment, rationale, requestCode, perms);
    }

}
