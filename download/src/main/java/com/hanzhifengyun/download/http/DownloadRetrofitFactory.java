package com.hanzhifengyun.download.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static com.hanzhifengyun.base.util.Preconditions.checkNotNull;


public class DownloadRetrofitFactory {
    private DownloadRetrofitFactory() {
    }

    public static Retrofit create(String baseUrl, OkHttpClient okHttpClient) {
        checkNotNull(baseUrl, "baseUrl cannot be null");
        checkNotNull(okHttpClient, "okHttpClient cannot be null");
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(okHttpClient)
                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder.build();
    }
}
