package com.hanzhifengyun.download.rx;

import com.hanzhifengyun.download.DownloadException;
import com.hanzhifengyun.download.listener.DownloadListener;
import com.hanzhifengyun.download.listener.ProgressListener;
import com.hanzhifengyun.download.model.DownloadCall;
import com.hanzhifengyun.download.model.DownloadTask;
import com.hanzhifengyun.download.model.DownloadState;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.hanzhifengyun.base.util.Preconditions.checkNotNull;


/**
 * 一次性观察者，无论回调哪个都结束观察
 */
public class DownloadObserver implements Observer<DownloadTask>, ProgressListener {
    private DownloadListener mDownloadListener;
    private DownloadTask mDownloadTask;


    public DownloadObserver(DownloadTask downloadTask) {
        this.mDownloadTask = checkNotNull(downloadTask);
        this.mDownloadListener = downloadTask.getDownloadListener();
    }

    @Override
    public void onComplete() {
        mDownloadTask.setState(DownloadState.FINISHED);
        if (mDownloadListener != null) {
            mDownloadListener.onFinished(mDownloadTask);
        }
    }

    @Override
    public void onError(Throwable e) {
        mDownloadTask.setState(DownloadState.ERROR);
        mDownloadTask.setDownloadException(new DownloadException(e));
        if (mDownloadListener != null) {
            mDownloadListener.onError(mDownloadTask);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDownloadTask.setState(DownloadState.DOWNLOADING);
        mDownloadTask.setCall(new DownloadCall(d));
        if (mDownloadListener != null) {
            mDownloadListener.beforeDownloading(mDownloadTask);
        }
    }

    @Override
    public void onNext(DownloadTask downloadTask) {
        this.mDownloadTask = downloadTask;
    }


    @Override
    public void update(long bytesRead, long contentLength, boolean finish) {
        if (mDownloadTask.getCountLength() > contentLength) {
            //断点续传
            bytesRead = mDownloadTask.getCountLength() - contentLength + bytesRead;
        } else {
            mDownloadTask.setCountLength(contentLength);
        }
        mDownloadTask.setReadLength(bytesRead);
        if (mDownloadListener != null && mDownloadTask.isDownloading()) {
            Observable.just(mDownloadTask)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<DownloadTask>() {
                        @Override
                        public void accept(DownloadTask downloadTask) throws Exception {
                            mDownloadListener.onDownloading(downloadTask);
                        }
                    });
        }
    }
}
