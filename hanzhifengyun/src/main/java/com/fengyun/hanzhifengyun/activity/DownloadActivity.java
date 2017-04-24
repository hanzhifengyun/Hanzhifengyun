package com.fengyun.hanzhifengyun.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.fengyun.hanzhifengyun.R;
import com.fengyun.hanzhifengyun.adapter.CommonListViewAdapter;
import com.fengyun.hanzhifengyun.adapter.CommonViewHolder;
import com.hanzhifengyun.download.data.DownloadRepository;
import com.hanzhifengyun.download.data.remote.DownloadRemoteApi;
import com.hanzhifengyun.download.listener.DownloadListener;
import com.hanzhifengyun.download.model.DownloadTask;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class DownloadActivity extends BaseActivity {

    private ListView mListView;

    private List<DownloadTask> mDownloadTaskList = new ArrayList<>();

    private DownloadRepository mDownloadRepository;

    private CommonListViewAdapter<DownloadTask> mAdapter;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        mListView = (ListView) findViewById(R.id.listView);
        mDownloadRepository = new DownloadRepository(new DownloadRemoteApi());

        requestPermissions();

        getData();

        mAdapter = new CommonListViewAdapter<DownloadTask>(this, mDownloadTaskList, R.layout.item_list_download) {
            @Override
            public void convert(CommonViewHolder viewHolder, final DownloadTask downloadTask, int position) {
                viewHolder.setText(R.id.tv_percent, downloadTask.getProgress() + "%");
                ProgressBar progressBar = viewHolder.getView(R.id.progressBar);
                progressBar.setProgress(downloadTask.getProgress());
                final Button button = viewHolder.getView(R.id.btn_download);
                if (downloadTask.isDownloading()) {
                    button.setText("暂停");
                } else if (downloadTask.isBeforeDownload()) {
                    button.setText("下载");
                } else if (downloadTask.isPause()) {
                    button.setText("继续下载");
                } else if (downloadTask.isStop()) {
                    button.setText("已停止下载");
                } else if (downloadTask.isFinshed()) {
                    button.setText("已完成");
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (downloadTask.isDownloading()) {
                            mDownloadRepository.pause(downloadTask);
                        } else if (downloadTask.isBeforeDownload()) {
                            mDownloadRepository.download(downloadTask);
                        } else if (downloadTask.isPause()) {
                            mDownloadRepository.download(downloadTask);
                        } else if (downloadTask.isStop()) {

                        } else if (downloadTask.isFinshed()) {

                        }
                    }
                });

            }
        };
        mListView.setAdapter(mAdapter);

    }

    private static final int REQUEST_PERMISSION_CODE = 102;

    @AfterPermissionGranted(REQUEST_PERMISSION_CODE)
    public void requestPermissions() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "",
                    REQUEST_PERMISSION_CODE, perms);
        } else {
            // Already have permission, do the thing
            // ...
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void getData() {
        for (int i = 0; i < 5; i++) {
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.setBaseUrl("http://www.baidu.com/")
                    .setSavePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/app-gmzh-release.apk")
                    .setDownloadUrl("http://bwp.oss-cn-beijing.aliyuncs.com/publish/app-shangnong/app-gmzh-release.apk");
            downloadTask.setDownloadListener(new DownloadListener() {
                @Override
                public void onFinished(DownloadTask downloadTask) {
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onDownloading(DownloadTask downloadTask) {
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onPause(DownloadTask downloadTask) {
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(DownloadTask downloadTask) {
                    mAdapter.notifyDataSetChanged();
                }
            });
            mDownloadTaskList.add(downloadTask);
        }
    }


}
