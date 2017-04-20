package com.hanzhifengyun.download.data.remote;


import com.hanzhifengyun.download.DownloadException;
import com.hanzhifengyun.download.http.DownloadOkHttpFactory;
import com.hanzhifengyun.download.http.DownloadRetrofitFactory;
import com.hanzhifengyun.download.listener.DownloadListener;
import com.hanzhifengyun.download.model.DownloadCall;
import com.hanzhifengyun.download.model.DownloadState;
import com.hanzhifengyun.download.model.DownloadTask;
import com.hanzhifengyun.download.rx.DownloadObserver;
import com.hanzhifengyun.download.rx.RetryWhenFunction;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Retrofit;


@Singleton
public class DownloadRemoteApi implements IDownloadRemoteApi {
    private static final String TAG = "UserRemoteApi";

    private Map<String, DownloadTask> mTaskMap;

    @Inject
    public DownloadRemoteApi() {
        mTaskMap = new HashMap<>();
    }


    @Override
    public void download(final DownloadTask downloadTask) {
        if (mTaskMap.get(downloadTask.getDownloadUrl()) != null) {
            //正在下载
            return;
        }
        mTaskMap.put(downloadTask.getDownloadUrl(), downloadTask);

        DownloadObserver observer = new DownloadObserver(downloadTask);
        OkHttpClient okHttpClient = DownloadOkHttpFactory.create(observer);
        Retrofit retrofit = DownloadRetrofitFactory.create(downloadTask.getBaseUrl(), okHttpClient);
        DownloadRemoteService downloadService = retrofit.create(DownloadRemoteService.class);

        downloadService.downloadFile(downloadTask.getDownloadUrl())
                 /*指定线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                   /*失败后的retry配置*/
                .retryWhen(new RetryWhenFunction())
                /*读取下载写入文件*/
                .map(new Function<ResponseBody, DownloadTask>() {
                    @Override
                    public DownloadTask apply(ResponseBody responseBody) throws Exception {
                        try {
//                            writeCache(responseBody, new File(downloadTask.getSavePath()), downloadTask);
                            File downloadedFile = new File(downloadTask.getSavePath());
                            BufferedSink sink = Okio.buffer(Okio.sink(downloadedFile));
                            sink.writeAll(responseBody.source());
                            sink.close();
                        } catch (IOException e) {
                            /*失败抛出异常*/
                            throw new DownloadException(e.getMessage());
                        }
                        mTaskMap.remove(downloadTask.getDownloadUrl());
                        return downloadTask;
                    }
                })
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*数据回调*/
                .subscribe(observer);
    }

    @Override
    public void stop(DownloadTask downloadTask) {
        downloadTask.setState(DownloadState.STOP);
        if (mTaskMap.containsKey(downloadTask.getDownloadUrl())) {
            DownloadCall call = downloadTask.getCall();
            if (call != null && !call.isCanceled()) {
                call.cancel();
            }
            mTaskMap.remove(downloadTask.getDownloadUrl());
        }
        DownloadListener downloadListener = downloadTask.getDownloadListener();
        if (downloadListener != null) {
            downloadListener.onStop(downloadTask);
        }
    }

    @Override
    public void pause(DownloadTask downloadTask) {
        downloadTask.setState(DownloadState.PAUSE);
        if (mTaskMap.containsKey(downloadTask.getDownloadUrl())) {
            DownloadCall call = downloadTask.getCall();
            if (call != null && !call.isCanceled()) {
                call.cancel();
            }
            mTaskMap.remove(downloadTask.getDownloadUrl());
        }
        DownloadListener downloadListener = downloadTask.getDownloadListener();
        if (downloadListener != null) {
            downloadListener.onPause(downloadTask);
        }
    }

    @Override
    public void stopAll() {
        for (DownloadTask task : mTaskMap.values()) {
            stop(task);
        }
    }

    @Override
    public void pauseAll() {
        for (DownloadTask task : mTaskMap.values()) {
            pause(task);
        }
    }

    @Override
    public void restartAll() {
        for (DownloadTask task : mTaskMap.values()) {
            download(task);
        }
    }

    /**
     * 写入文件
     *
     * @param file
     * @param info
     * @throws IOException
     */
    public void writeCache(ResponseBody responseBody, File file, DownloadTask info) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        long allLength;
        if (info.getCountLength() == 0) {
            allLength = responseBody.contentLength();
        } else {
            allLength = info.getCountLength();
        }
        FileChannel channelOut = null;
        RandomAccessFile randomAccessFile = null;
        randomAccessFile = new RandomAccessFile(file, "rwd");
        channelOut = randomAccessFile.getChannel();
        InputStream inputStream = responseBody.byteStream();
        MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                info.getReadLength(), allLength - info.getReadLength());
        byte[] buffer = new byte[1024 * 8];
        int len;
        int record = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            mappedBuffer.put(buffer, 0, len);
            record += len;
        }
        inputStream.close();
        if (channelOut != null) {
            channelOut.close();
        }
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
    }
}
