package com.hanzhifengyun.download.data.remote;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;



public interface DownloadRemoteService {

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Header("RANGE") String start, @Url String downloadUrl);


    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String downloadUrl);
}
