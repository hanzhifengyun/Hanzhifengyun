package com.hanzhifengyun.base.common.rx;


import com.hanzhifengyun.base.model.BaseERPModel;
import com.hanzhifengyun.base.util.exception.UnauthorizedException;
import com.hanzhifengyun.base.util.handler.IJsonHandler;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.hanzhifengyun.base.util.Preconditions.checkNotNull;


public class CheckStringCookieFunction implements Function<String, ObservableSource<String>> {

    private IJsonHandler mJsonHandler;
    private Type mType;


    public CheckStringCookieFunction(IJsonHandler jsonHandler, Type type) {
        this.mJsonHandler = checkNotNull(jsonHandler);
        this.mType = type;
    }

    @Override
    public ObservableSource<String> apply(String json) throws Exception {
        BaseERPModel baseERPModel = mJsonHandler.fromJson(json, mType);
        if (baseERPModel.isUnauthorized()) {
            return Observable.error(new UnauthorizedException());
        }
        return Observable.just(json);
    }
}
