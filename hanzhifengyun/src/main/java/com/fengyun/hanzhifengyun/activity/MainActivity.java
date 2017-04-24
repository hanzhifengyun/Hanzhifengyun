package com.fengyun.hanzhifengyun.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fengyun.hanzhifengyun.R;
import com.fengyun.hanzhifengyun.adapter.CommonListViewAdapter;
import com.fengyun.hanzhifengyun.adapter.CommonViewHolder;
import com.fengyun.hanzhifengyun.util.ActivityCollector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ListView mListView;
    private List<String> mListViewData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        mListView.setAdapter(new CommonListViewAdapter<String>(MainActivity.this, mListViewData, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(CommonViewHolder viewHolder, String s, int position) {
                viewHolder.setText(android.R.id.text1, s);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showShortToast(position + "");
                switch (position){
                    case 1: {
                        intent2Activity(LockPatternSettingActivity.class);
                        break;
                    }
                    case 2: {
                        ActivityCollector.finishAll();
                        break;
                    }
                    case 3: {
                        intent2Activity(DownloadActivity.class);
                        break;
                    }
                    case 4: {
                        intent2Activity(RxBusActivity.class);
                        break;
                    }
                }

            }
        });
    }

    private void initData() {
        String[] items = getResources().getStringArray(R.array.activity_startup);
        mListViewData = Arrays.asList(items);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.activity_startup_listView);
    }


    //由于用户需连续点击两次返回键才能退出,需记录前一次点击的时间
    private long lastClickTime = 0;

    // 点击返回键时,这里重写方法
    @Override
    public void onBackPressed()
    {
        if (lastClickTime <= 0)
        {
            showShortToast(getString(R.string.please_click_one_more_time));
            lastClickTime = System.currentTimeMillis();
        }
        else
        {
            //判断两次点击的时间间隔是否小于3秒,如果小于则退出程序,否则再次提示"请再按一次后退出"
            if (System.currentTimeMillis() - lastClickTime < 3000)
            {
                finish();
            }
            else
            {
                showShortToast(getString(R.string.please_click_one_more_time));
                lastClickTime = System.currentTimeMillis();
            }
        }
    }



}
