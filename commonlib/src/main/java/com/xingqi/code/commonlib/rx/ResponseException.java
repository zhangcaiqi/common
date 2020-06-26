package com.xingqi.code.commonlib.rx;

public class ResponseException extends Exception{

    private int code;

    public String message;


    public ResponseException(Throwable throwable, int code){
        super(throwable);
        this.code = code;
    }


}
