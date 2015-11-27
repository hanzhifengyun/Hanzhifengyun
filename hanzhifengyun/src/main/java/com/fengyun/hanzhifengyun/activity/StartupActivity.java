package com.fengyun.hanzhifengyun.activity;

import android.os.Bundle;

import com.fengyun.hanzhifengyun.fragment.LockPatternFragment;
import com.fengyun.hanzhifengyun.util.LockPatternUtil;

public class StartupActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LockPatternUtil.isLockPatternSetting(StartupActivity.this))//说明还未设置过密码,跳转到设置密码页面
        {
            intent2Activity(MainActivity.class);
        } else//说明已设置密码,跳转到输入密码页面
        {
            LockPatternActivity.actionStart(StartupActivity.this, LockPatternFragment.LOGIN_TYPE_CHECK);
        }
        finish();
    }

}
