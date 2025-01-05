package com.github.jusearch.exception;

/**
 * @author iusie
 * @description
 * @date 2024/12/25
 */
public class ThrowUtils {
    /**
     * 条件成立, 抛异常
     *
     * @param condition 条件
     * @param runtimeException 异常
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException){
        if (condition)
        {
            throw runtimeException;
        }
    }

    /**
     * 条件成立, 抛异常
     *
     * @param condition 条件
     * @param stateCode 状态码
     */
    public static void throwIf(boolean condition, StateCode stateCode){
        throwIf(condition,new BusinessException(stateCode));
    }

    public static void throwIf(boolean condition, StateCode stateCode, String message){
        throwIf(condition,new BusinessException(stateCode, message));
    }

}
