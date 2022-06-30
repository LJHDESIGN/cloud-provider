package com.cloud.cloudprovider.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @author linjianhui
 * @description
 * @date 2022/6/29 4:57 下午
 */
public class HandlerException {

    public static String allHandlerException(BlockException exception){
        return "服务繁忙，请稍后再试："+ exception.getClass().getCanonicalName() +"\tallHandleException---";
    }

    /**
     * 注意！！！！此处返回类型和参数必须与原函数返回类型和参数一致！！！！！！
     * 所以会配置一般会使用通用返回类型，并无参数的作为通用返回Handler。
     * @param exception 限流异常
     */
    public static String handlerException(String name,BlockException exception){
        return "服务繁忙，请稍后再试："+ exception.getClass().getCanonicalName() +"\thandleException---" + name;
    }
}
