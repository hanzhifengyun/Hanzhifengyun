package com.hanzhifengyun.download.listener;

/**
 * 下载进度回调
 */

public interface ProgressListener {
    /**
     * 下载进度
     * @param bytesRead 已下载长度
     * @param contentLength  总长度
     * @param finish  是否完成
     */
    void update(long bytesRead, long contentLength, boolean finish);
}
