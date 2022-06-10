package com.tangzhiye.wj.result;

public class ResultFactory {

    public static Result buildSuccessResult(Object data){
        return buildResult(ResultCode.SUCCESS, "成功", data);
    }

    public static Result buildFailResult(String message){
        return buildResult(ResultCode.FAIL, message, null);
    }

    public static Result buildResult(ResultCode resultCode, String message, Object data){
        return new Result(resultCode.code, message, data);
    }
}
