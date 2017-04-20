package com.hanzhifengyun.download.data;

import com.hanzhifengyun.download.data.remote.IDownloadRemoteApi;
import com.hanzhifengyun.download.model.DownloadTask;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.hanzhifengyun.base.util.Preconditions.checkNotNull;

@Singleton
public class DownloadRepository implements IDownloadRemoteApi {
    private static final String TAG = DownloadRepository.class.getSimpleName();
    private IDownloadRemoteApi mRemoteApi;


    @Inject
    public DownloadRepository(IDownloadRemoteApi updateRemoteApi) {
        mRemoteApi = checkNotNull(updateRemoteApi);

    }


    @Override
    public void download(DownloadTask downloadTask) {
        checkNotNull(downloadTask);

        mRemoteApi.download(downloadTask);
    }


    public void stop(DownloadTask downloadTask) {
        checkNotNull(downloadTask);

        mRemoteApi.stop(downloadTask);
    }

    @Override
    public void pause(DownloadTask downloadTask) {
        checkNotNull(downloadTask);

        mRemoteApi.pause(downloadTask);
    }

    @Override
    public void stopAll() {
        mRemoteApi.stopAll();
    }

    @Override
    public void pauseAll() {
        mRemoteApi.pauseAll();
    }

    @Override
    public void restartAll() {
        mRemoteApi.restartAll();
    }
}
