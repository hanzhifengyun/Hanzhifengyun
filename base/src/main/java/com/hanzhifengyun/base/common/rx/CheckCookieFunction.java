package com.hanzhifengyun.base.common.rx;


import com.hanzhifengyun.base.model.BaseERPModel;
import com.hanzhifengyun.base.util.exception.UnauthorizedException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class CheckCookieFunction<T> implements Function<BaseERPModel<T>, ObservableSource<BaseERPModel<T>>> {


    @Override
    public ObservableSource<BaseERPModel<T>> apply(BaseERPModel<T> tBaseERPModel) throws Exception {
        if (tBaseERPModel.isUnauthorized()) {
            return Observable.error(new UnauthorizedException());
        }
        return Observable.just(tBaseERPModel);
    }
}
