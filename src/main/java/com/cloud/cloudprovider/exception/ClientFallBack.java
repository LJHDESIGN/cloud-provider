package com.cloud.cloudprovider.exception;

/**
 * @author linjianhui
 * @description
 * @date 2022/6/29 5:06 下午
 */
public class ClientFallBack {

    public static String allFallBack(){
        return "服务开小差了，请稍后再试：" + "\t allHandleException---";
    }

    public static String fallBackA(String name){
        return "服务开小差了，请稍后再试：" + "\thandleException---" + name;
    }
}
