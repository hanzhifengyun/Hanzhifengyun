package com.hanzhifengyun.base.util.http.factory;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hanzhifengyun.base.util.LogUtil;
import com.hanzhifengyun.base.util.http.CookieInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * OkHttp创建
 */
public class OkHttpFactory {
    private static final String TAG = "OkHttpFactory";
    private final static int CONNECT_TIMEOUT = 10;
    private final static int WRITE_TIMEOUT = 10;
    private final static int READ_TIMEOUT = 10;

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    @NonNull
    public static OkHttpClient create(Context context) {
        return create(context, false);
    }

    @NonNull
    public static OkHttpClient create(Context context, boolean isDebug) {
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
        if (isDebug) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        builder.addInterceptor(loggingInterceptor);
        builder.addInterceptor(new CookieInterceptor());
        return builder.build();
    }

    public static RequestBody getRequestBody(String method, Object... params) {
        String json = JsonFactory.getJson(method, params);
        return getRequestBody(json);
    }

    @NonNull
    public static RequestBody getRequestBody(String json) {
        LogUtil.i(TAG, json);
        if (json == null) {
            json = "";
        }
        return RequestBody.create(MEDIA_TYPE_JSON, json);
    }

}
