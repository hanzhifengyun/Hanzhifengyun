package com.hanzhifengyun.base.common.rx;


import com.hanzhifengyun.base.model.BaseERPModel;
import com.hanzhifengyun.base.util.exception.MyRuntimeException;
import com.hanzhifengyun.base.util.handler.IJsonHandler;

import java.lang.reflect.Type;

import io.reactivex.functions.Function;

import static com.hanzhifengyun.base.util.Preconditions.checkNotNull;


public class StringToResultFunction<T> implements Function<String, T> {

    private IJsonHandler mJsonHandler;
    private Type mType;


    public StringToResultFunction(IJsonHandler jsonHandler, Type type) {
        this.mJsonHandler = checkNotNull(jsonHandler);
        this.mType = type;
    }

    @Override
    public T apply(String json) throws Exception {
        BaseERPModel<T> baseERPModel = mJsonHandler.fromJson(json, mType);
        if (baseERPModel == null) {
            throw new MyRuntimeException("获取数据为空");
        }
        if (!baseERPModel.isSuccessful()) {
            throw new MyRuntimeException(baseERPModel.getError().getMessage());
        }

        T t = baseERPModel.getResult();
        if (t == null) {
            t = (T) "";
        }
        return t;
    }
}
