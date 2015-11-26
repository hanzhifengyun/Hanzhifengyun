package com.fengyun.hanzhifengyun.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.fengyun.hanzhifengyun.util.ActivityCollector;
import com.fengyun.hanzhifengyun.util.ToastUtil;


/**
 * Created by fengyun on 2015/10/20.
 */
public class BaseActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    protected void showShortToast(String content){
        ToastUtil.showToast(this, content, Toast.LENGTH_SHORT);
    }
}
