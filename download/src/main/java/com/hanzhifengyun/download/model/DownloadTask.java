package com.hanzhifengyun.download.model;

import com.hanzhifengyun.download.DownloadException;
import com.hanzhifengyun.download.listener.DownloadListener;

/**
 * 下载信息
 */

public class DownloadTask {
    /*存储位置*/
    private String savePath;
    /*下载url*/
    private String downloadUrl;
    /*基础url*/
    private String baseUrl;
    /*文件总长度*/
    private long countLength;
    /*下载长度*/
    private long readLength;
    /*下载状态*/
    private DownloadState state = DownloadState.BEFORE;


    private DownloadListener downloadListener;

    private DownloadException downloadException;

    private DownloadCall call;


    public int getProgress() {
        if (countLength <= 0) {
            return 0;
        }
        return (int) (100 * readLength / countLength);
    }


    public DownloadCall getCall() {
        return call;
    }

    public DownloadTask setCall(DownloadCall call) {
        this.call = call;
        return this;
    }

    public DownloadException getError() {
        return downloadException;
    }

    public DownloadTask setDownloadException(DownloadException downloadException) {
        this.downloadException = downloadException;
        return this;
    }

    public String getSavePath() {
        return savePath;
    }

    public DownloadTask setSavePath(String savePath) {
        this.savePath = savePath;
        return this;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public DownloadTask setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public DownloadTask setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public long getCountLength() {
        return countLength;
    }

    public DownloadTask setCountLength(long countLength) {
        this.countLength = countLength;
        return this;
    }

    public long getReadLength() {
        return readLength;
    }

    public DownloadTask setReadLength(long readLength) {
        this.readLength = readLength;
        return this;
    }

    public DownloadState getState() {
        return state;
    }

    public DownloadTask setState(DownloadState state) {
        this.state = state;
        return this;
    }

    public DownloadListener getDownloadListener() {
        return downloadListener;
    }

    public DownloadTask setDownloadListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
        return this;
    }

    public boolean isDownloading() {
        return state == DownloadState.DOWNLOADING;
    }

    public boolean isBeforeDownload() {
        return state == DownloadState.BEFORE;
    }

    public boolean isPause() {
        return state == DownloadState.PAUSE;
    }

    public boolean isStop() {
        return state == DownloadState.STOP;
    }

    public boolean isFinshed() {
        return state == DownloadState.FINISHED;
    }

    public boolean isError() {
        return state == DownloadState.ERROR;
    }
}
