package com.hanzhifengyun.download.http;

import android.support.annotation.NonNull;

import com.hanzhifengyun.base.util.LogUtil;
import com.hanzhifengyun.download.listener.ProgressListener;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * OkHttp创建
 */
public class DownloadOkHttpFactory {
    private static final String TAG = "DownloadOkHttpFactory";
    private final static int CONNECT_TIMEOUT = 20;
    private final static int WRITE_TIMEOUT = 20;
    private final static int READ_TIMEOUT = 20;


    @NonNull
    public static OkHttpClient create(ProgressListener listener) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.i(TAG, message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        builder.addInterceptor(new DownloadInterceptor(listener));
        return builder.build();
    }

}
