package com.cloud.cloudprovider.util;

import com.cloud.cloudprovider.config.NacosConfig;
import org.springframework.stereotype.Component;

/**
 * @author linjianhui
 * @description
 * @date 2023/3/1 16:21
 */
@Component
public class TestConfigUtil {

    public static NacosConfig NacosConfig;

    public TestConfigUtil(NacosConfig nacosConfig){
        NacosConfig = nacosConfig;
    }

    public static String getAA(){
//        String a = NacosConfig.userName;
//        return NacosConfig.userName();
        return null;
    }

    public static String testCommitA(){
        String a = "NacosConfig.userName";
        return null;
    }

    public static String testCommitB(){
        String a = "NacosConfig.B";
        return null;
    }

}
