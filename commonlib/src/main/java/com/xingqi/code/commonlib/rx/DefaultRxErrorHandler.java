package com.xingqi.code.commonlib.rx;

import android.net.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class DefaultRxErrorHandler implements RxErrorHandler {
    private static ResponseBody body;
    @Override
    public ResponseException handleException(Throwable e) {
        ResponseException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseException(e, HTTP_ERROR);
            switch (httpException.code()) {
                case RxErrorHandler.UNAUTHORIZED:
                   /* body = ((HttpException) e).response().errorBody();
                    try {
                        String message = body.string();
                        Gson gson = new Gson();
                        Exception_401DTO exceptionDTO_401 = gson.fromJson(message, Exception_401DTO.class);
                        //[size=106 text={"error":"invalid_token","error_description":"Invalid access tok…]
                        if (exceptionDTO_401.getError().toString().equals("invalid_token")) {
                            ex.message = "请退出后登录";
                            //发出广播，然后跳转登录
                            ToLoginBean toLoginBean = new ToLoginBean();
                            toLoginBean.setFlag(true);
                            EventBus.getDefault().post(toLoginBean);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }*/
                    break;
                case FORBIDDEN:
                    ex.message = "服务器已经理解请求，但是拒绝执行它";
                    break;
                case NOT_FOUND:
                    ex.message = "服务器异常，请稍后再试";
                    break;
                case REQUEST_TIMEOUT:
                    ex.message = "请求超时";
                    break;
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                    ex.message = "服务器遇到了一个未曾预料的状况，无法完成对请求的处理";
                    break;
                case BAD_REQUEST:
                    /*body = ((HttpException) e).response().errorBody();
                    try {
                        String message = body.string();
                        Gson gson = new Gson();
                        Exception_401DTO exceptionDTO_401 = gson.fromJson(message, Exception_401DTO.class);
                        //[size=106 text={"error":"invalid_token","error_description":"Invalid access tok…]
                        *//**
                 * {"error":"invalid_grant","error_description":"Bad credentials"}
                 *//*
                        if (exceptionDTO_401.getError().toString().equals("invalid_grant")) {
                            ex.message = "用户名或密码错误";
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }*/
                    break;
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                case FAIL_QUEST:
                    body = ((HttpException) e).response().errorBody();
                    try {
                        String message = body.string();
                        Gson gson = new Gson();
                        ErrorBody globalExceptionDTO = gson.fromJson(message, ErrorBody.class);
                        if (globalExceptionDTO.getErrMsg() != null) {
                            ex.message = globalExceptionDTO.getErrMsg();
                        } else {
                            ex.message = "";
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                default:
                    ex.message = "网络错误";
                    break;
            }
            return ex;
        } else if (e instanceof RuntimeException) {
            ex = new ResponseException(e, 0);
            ex.message = "运行时异常";
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponseException(e, PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponseException(e, NETWORD_ERROR);
            ex.message = "连接失败";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseException(e, SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ResponseException(e, TIMEOUT_ERROR);
            //ex.message = "连接超时";
            ex.message = "当前网络连接不顺畅，请稍后再试！";
            return ex;
        } else if (e instanceof java.net.UnknownHostException) {
            ex = new ResponseException(e, TIMEOUT_ERROR);
            ex.message = "网络中断，请检查网络状态！";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLException) {
            ex = new ResponseException(e, TIMEOUT_ERROR);
            ex.message = "网络中断，请检查网络状态！";
            return ex;
        } else if (e instanceof java.io.EOFException) {
            ex = new ResponseException(e, PARSE_EmptyERROR);
            ex.message = "1007";
            return ex;
        } else if (e instanceof java.lang.NullPointerException) {
            ex = new ResponseException(e, PARSE_EmptyERROR);
            ex.message = "数据为空，显示失败";
            return ex;
        } else {
            ex = new ResponseException(e, UNKNOWN);
            ex.message = "未知错误";
            return ex;
        }
    }
}
