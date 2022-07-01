package com.cloud.cloudprovider.service.impl;

import com.cloud.cloudprovider.config.NacosConfig;
import com.cloud.cloudprovider.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @author linjianhui
 * @description
 * @date 2022/6/29 6:18 下午
 */
@Slf4j
@Service
public class HelloServiceImpl implements HelloService {

    @Resource
    private NacosConfig nacosConfig;

    @Override
    public String hi(String name) {
        return "serviceTestA "+ name;
    }

    @Override
    public String get() {
        return nacosConfig.getUsername();
    }
}
