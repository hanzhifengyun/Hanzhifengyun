package com.hanzhifengyun.download.model;

import io.reactivex.disposables.Disposable;

/**
 *
 */

public class DownloadCall {

    private final Disposable mDisposable;

    public DownloadCall(Disposable disposable) {
        mDisposable = disposable;
    }

    public boolean isCanceled() {
        return mDisposable.isDisposed();
    }

    public void cancel() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
