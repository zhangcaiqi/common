package com.xingqi.code.commonlib.mvp;

public interface IRepositoryManager {

    <T> T obtainRetrofitService(String baseUrl,Class<T> serviceClass);
}
