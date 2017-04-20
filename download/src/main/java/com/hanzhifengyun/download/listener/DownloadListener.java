package com.hanzhifengyun.download.listener;

import com.hanzhifengyun.download.model.DownloadTask;

/**
 * 下载过程回调
 */

public abstract class DownloadListener {
    /**
     * 下载成功
     */
    public abstract void onFinished(DownloadTask downloadTask);

    /**
     * 下载中，进度回调
     */
    public abstract void onDownloading(DownloadTask downloadTask);

    /**
     * 下载失败
     */
    public abstract void onError(DownloadTask downloadTask);


    /**
     * 开始下载前
     */
    public void beforeDownloading(DownloadTask downloadTask) {

    }

    /**
     * 暂停下载
     */
    public void onPause(DownloadTask downloadTask) {

    }

    /**
     * 停止下载销毁
     */
    public void onStop(DownloadTask downloadTask) {

    }

}
