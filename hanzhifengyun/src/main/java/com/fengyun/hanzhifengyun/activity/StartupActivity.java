package com.fengyun.hanzhifengyun.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.fengyun.hanzhifengyun.R;
import com.fengyun.hanzhifengyun.adapter.CommonListViewAdapter;
import com.fengyun.hanzhifengyun.adapter.CommonViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartupActivity extends BaseActivity {
    private ListView mListView;
    private List<String> mListViewData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        mListView.setAdapter(new CommonListViewAdapter<String>(StartupActivity.this,mListViewData,android.R.layout.simple_list_item_1) {
            @Override
            public void convert(CommonViewHolder viewHolder, String s, int position) {
                viewHolder.setText(android.R.id.text1,s);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showShortToast(position + "");
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





}
