package com.cloud.cloudprovider.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author linjianhui
 * @description
 * @date 2022/7/1 10:18 上午
 */
@Component
@RefreshScope
@Data
public class NacosConfig {

    @Value("${cloud.username:你好}")
    public String username;

}
