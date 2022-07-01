package com.cloud.cloudprovider.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.cloud.cloudprovider.exception.ClientFallBack;
import com.cloud.cloudprovider.exception.HandlerException;
import com.cloud.cloudprovider.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author linjianhui
 * @description
 * @date 2022/6/29 2:05 下午
 */
@RestController
@RequestMapping("hello")
public class HelloController {

    @Resource
    private HelloService helloService;

    @GetMapping("/serviceTestA")
    @SentinelResource(value="hello/serviceTestA",
            blockHandlerClass = HandlerException.class,
            blockHandler = "handlerException",
            fallbackClass = ClientFallBack.class,
            fallback = "fallBackA")
    public String serviceTestA(@RequestParam(value = "name",defaultValue= "forezp",required = false) String name){
        return helloService.hi(name);
    }

    @RequestMapping("/username")
    public String get() {
        return helloService.get();
    }
}
