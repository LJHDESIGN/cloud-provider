package com.cloud.cloudprovider.model;

import com.cloud.cloudprovider.model.User2;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author linjianhui
 * @description
 * @date 2022/7/11 4:47 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class User2Son extends User2 {
    private String son;
}
