package com.cloud.cloudprovider.service.impl;

import com.cloud.cloudprovider.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author linjianhui
 * @description
 * @date 2022/6/29 6:18 下午
 */
@Slf4j
@Service
public class HelloServiceImpl implements HelloService {


    @Override
    public String hi(String name) {
        return "serviceTestA "+ name;
    }
}
