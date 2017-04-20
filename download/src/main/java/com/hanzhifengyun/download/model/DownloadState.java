package com.hanzhifengyun.download.model;

/**
 * 下载状态
 */

public enum DownloadState {
    /**
     * 开始前
     */
    BEFORE,
    /**
     * 下载中
     */
    DOWNLOADING,
    /**
     * 暂停
     */
    PAUSE,
    /**
     * 停止
     */
    STOP,
    /**
     * 下载错误
     */
    ERROR,
    /**
     * 下载完成
     */
    FINISHED,
}
