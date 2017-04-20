package com.hanzhifengyun.download.data.remote;


import com.hanzhifengyun.download.model.DownloadTask;

public interface IDownloadRemoteApi {

    /**
     * 下载
     */
    void download(DownloadTask downloadTask);


    /**
     * 停止
     */
    void stop(DownloadTask downloadTask);


    /**
     * 暂停
     */
    void pause(DownloadTask downloadTask);


    void stopAll();


    void pauseAll();


    void restartAll();



}
