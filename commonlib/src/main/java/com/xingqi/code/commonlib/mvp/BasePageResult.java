package com.xingqi.code.commonlib.mvp;


import java.util.List;

public class BasePageResult<T> {

    public int code;

    public Data<T> data;

    public class Data<T>{

       public int total;

       public List<T> items;
    }
}
