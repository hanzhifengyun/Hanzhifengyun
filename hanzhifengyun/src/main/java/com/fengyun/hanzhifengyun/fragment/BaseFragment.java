package com.fengyun.hanzhifengyun.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.fengyun.hanzhifengyun.util.ToastUtil;

/**
 * Created by fengyun on 2015/11/27.
 */
public class BaseFragment extends Fragment {

    protected void showShortToast(String content){
        ToastUtil.showShortToast(getActivity(), content);
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(getActivity(), tarActivity);
        startActivity(intent);
    }
}
