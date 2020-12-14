package com.zhangjp.doc.converter.base;

import lombok.Data;

/**
 * 描述: 响应封装结果
 */
@Data
public class BaseResponse<T> {

    private static final String MSG_DEFAULT = "success";

    private String msg;

    private T data;

    private String code;

    private Boolean success;

    public static<T> BaseResponse<T> success(T obj) {
        BaseResponse<T> result = new BaseResponse<>();
        result.setSuccess(true);
        result.setData(obj);
        result.setCode(ResponseStatus.SUCCESS.value());
        result.setMsg(MSG_DEFAULT);
        return result;
    }


    public static<T> BaseResponse<T> fail(String failMessage) {
        BaseResponse<T> result = new BaseResponse<>();
        result.setSuccess(false);
        result.setCode(ResponseStatus.ERROR.value());
        result.setMsg(failMessage);
        return result;
    }


    public static<T> BaseResponse<T> fail(String failMessage,T obj) {
        BaseResponse<T> result = new BaseResponse<>();
        result.setSuccess(false);
        result.setCode(ResponseStatus.ERROR.value());
        result.setMsg(failMessage);
        result.setData(obj);
        return result;
    }


    public static<T> BaseResponse<T> fail(String code,String failMessage) {
        BaseResponse<T> result = new BaseResponse<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(failMessage);
        return result;
    }


}
