package com.github.jusearch.common;

import com.github.jusearch.exception.StateCode;

/**
 * 返回工具类
 */
public class ResultUtils {


    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, data, "ok");
    }

    public static <T> BaseResponse<T> success(T data, String massage) {
        return new BaseResponse<>(200, data, massage);
    }

    /**
     * 失败
     *
     * @param stateCode
     * @return
     */
    public static BaseResponse<?> error(StateCode stateCode) {
        return new BaseResponse<>(stateCode);
    }

    public static BaseResponse<?> error(int code,String message){
        return new BaseResponse<>(code,null,message);
    }
    public static BaseResponse<?> error(StateCode stateCode,String message){
        return new BaseResponse<>(stateCode.getCode(),null,message);
    }
}
