package com.xingqi.code.commonlib.utils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2019/6/4.
 */

public class OkHttpUtil {

    public static void get(String url, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url).build();

        client.newCall(request).enqueue(callback);
    }
    public static void get(String url, String sessionid,Callback callback){
        OkHttpClient client = new OkHttpClient();

        Request request =new Request.Builder().url(url).addHeader("cookie",sessionid).build();

        client.newCall(request).enqueue(callback);
    }
    public static void post(String url, Map<String,String> data, Callback callback){
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        for(String key:data.keySet()){
            builder.add(key,data.get(key));
        }
        RequestBody requestBody = builder.build();

        Request request = new Request.Builder().url(url).post(requestBody).build();

        client.newCall(request).enqueue(callback);
    }

    public static void post(String url, String json, Callback callback){
        RequestBody requestBody
                = FormBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }
    public static void upload(String url, File file,Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + UUID.randomUUID())
                .url(url)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static String getSessionId(Response response){
        Headers headers = response.headers();
        List<String> cookies = headers.values("Set-Cookie");
        String session = cookies.get(0);
        String s = session.substring(0, session.indexOf(";"));
        return s;
    }
    public static void postFile( String url,
                             Map<String, String> map,
                             File file,Callback callback) {
        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(file != null){
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("pic", file.getName(), body);
        }
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody.build())
                .build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder()
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .build()
                .newCall(request).enqueue(callback);

    }
}
