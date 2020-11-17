package com.xingqi.code.commonlib.mvp;

public class BaseResult<T> extends Result{


    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
