package com.xingqi.code.commonlib.mvp;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryManager implements IRepositoryManager {

    private Map<String, Retrofit> mRetrofitMap = new HashMap<>();
    private RepositoryManager(){

    }
    public static RepositoryManager getSingleton(){
        return RepositoryManagerHolder.repositoryManager;
    }
    private static class RepositoryManagerHolder{
        private static final RepositoryManager repositoryManager = new RepositoryManager();
    }
    private Retrofit getRetrofit(String baseUrl){
        Retrofit retrofit;
        if(mRetrofitMap.containsKey(baseUrl)){
            retrofit = mRetrofitMap.get(baseUrl);
        }else{
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mRetrofitMap.put(baseUrl,retrofit);
        }
        return retrofit;
    }
    @Override
    public <T> T obtainRetrofitService(String baseUrl,Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new RetrofitServiceProxyHandler(getRetrofit(baseUrl), serviceClass));
    }
}
