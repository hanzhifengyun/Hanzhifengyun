package com.hanzhifengyun.download;

/**
 * 自定义异常
 */
public class DownloadException extends Exception {
    public DownloadException() {
    }

    public DownloadException(String detailMessage) {
        super(detailMessage);
    }

    public DownloadException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DownloadException(Throwable throwable) {
        super(throwable);
    }


    @Override
    public String getMessage() {
        if (super.getMessage() == null) {
            return "unknown error";
        }
        return super.getMessage();
    }
}
