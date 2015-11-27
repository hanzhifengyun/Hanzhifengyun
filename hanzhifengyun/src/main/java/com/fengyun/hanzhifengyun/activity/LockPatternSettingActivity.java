package com.fengyun.hanzhifengyun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fengyun.hanzhifengyun.R;
import com.fengyun.hanzhifengyun.fragment.LockPatternFragment;
import com.fengyun.hanzhifengyun.util.LockPatternUtil;

public class LockPatternSettingActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUEST_CLOSE = 0;
    public static final int REQUEST_SETTING = 1;

    private ImageView switchImageView;
    private LinearLayout updateLayout;

    private boolean isLockPatternOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_pattern_setting);

        initView();
        initEvent();
    }

    private void initEvent() {
        switchImageView.setOnClickListener(this);
        updateLayout.setOnClickListener(this);
    }

    private void initView() {
        isLockPatternOpen = LockPatternUtil.isLockPatternSetting(LockPatternSettingActivity.this);
        switchImageView = (ImageView) findViewById(R.id.activity_lockPatternSetting_imageView_switch);
        switchImageView.setImageResource(isLockPatternOpen ? R.drawable.switch_on : R.drawable.switch_off);
        updateLayout = (LinearLayout) findViewById(R.id.activity_lockPatternSetting_update);
        updateLayout.setVisibility(isLockPatternOpen ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_lockPatternSetting_update: {
                LockPatternActivity.actionStart(LockPatternSettingActivity.this, LockPatternFragment.LOGIN_TYPE_UPDATE);
                break;
            }
            case R.id.activity_lockPatternSetting_imageView_switch: {
                if (isLockPatternOpen) {
                    LockPatternActivity.actionStartForResult(LockPatternSettingActivity.this, LockPatternFragment.LOGIN_TYPE_CLOSE, REQUEST_CLOSE);
                } else {
                    LockPatternActivity.actionStartForResult(LockPatternSettingActivity.this, LockPatternFragment.LOGIN_TYPE_SETTINGS, REQUEST_SETTING);
                }
                break;
            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CLOSE: {
                    //关闭图案功能成功
                    isLockPatternOpen = false;
                    updateLayout.setVisibility(View.INVISIBLE);
                    switchImageView.setImageResource(R.drawable.switch_off);
                    break;
                }
                case REQUEST_SETTING: {
                    //设置密码成功
                    isLockPatternOpen = true;
                    updateLayout.setVisibility(View.VISIBLE);
                    switchImageView.setImageResource(R.drawable.switch_on);
                    break;
                }
            }
        } else {
            switch (requestCode) {
                case REQUEST_CLOSE: {
                    //关闭图案功能失败
                    switchImageView.setImageResource(R.drawable.switch_on);
                    updateLayout.setVisibility(View.VISIBLE);
                    break;
                }
                case REQUEST_SETTING: {
                    //设置密码失败
                    updateLayout.setVisibility(View.INVISIBLE);
                    switchImageView.setImageResource(R.drawable.switch_off);
                    break;
                }
            }
        }
    }
}
