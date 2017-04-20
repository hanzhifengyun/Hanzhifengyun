package com.hanzhifengyun.download.rx;


import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


public class RetryWhenFunction implements Function<Observable<Throwable>, Observable<?>> {


    private int count;

    private static final int COUNT_MAX = 3;

    public RetryWhenFunction() {
        count = 0;
    }

    @Override
    public Observable<?> apply(Observable<Throwable> throwableObservable) throws Exception {
        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                if (throwable instanceof ConnectException
                        || throwable instanceof SocketTimeoutException
                        || throwable instanceof TimeoutException) {
                    if (count++ < COUNT_MAX) {
                        return Observable.just(count);
                    }
                }
                return Observable.error(throwable);
            }
        });
    }
}
