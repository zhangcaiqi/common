package com.xingqi.code.commonlib.rx;

public interface RxErrorHandler {

    ResponseException handleException(Throwable e);

    /**
     * 未知错误
     */
    int UNKNOWN = 1000;
    /**
     * 解析错误
     */
    int PARSE_ERROR = 1001;
    /**
     * 解析no content错误
     */
    int PARSE_EmptyERROR = 1007;
    /**
     * 网络错误
     */
    int NETWORD_ERROR = 1002;
    /**
     * 协议出错
     */
    int HTTP_ERROR = 1003;

    /**
     * 证书出错
     */
    int SSL_ERROR = 1005;

    /**
     * 连接超时
     */
    int TIMEOUT_ERROR = 1006;

    int LOGIN_ERROR = -1000;
    int DATA_EMPTY = -2000;


    int UNAUTHORIZED = 401;
    int FORBIDDEN = 403;
    int NOT_FOUND = 404;
    int REQUEST_TIMEOUT = 408;
    int INTERNAL_SERVER_ERROR = 500;
    int BAD_GATEWAY = 502;
    int SERVICE_UNAVAILABLE = 503;
    int GATEWAY_TIMEOUT = 504;
    int FAIL_QUEST = 406;//无法使用请求的内容特性来响应请求的网页
    int BAD_REQUEST = 400;
}
