package com.hanzhifengyun.base.common.rx;


import com.hanzhifengyun.base.model.BaseERPModel;
import com.hanzhifengyun.base.util.exception.MyRuntimeException;

import io.reactivex.functions.Function;

public class ResultFunction<T> implements Function<BaseERPModel<T>, T> {


    @Override
    public T apply(BaseERPModel<T> baseERPModel) throws Exception {
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
