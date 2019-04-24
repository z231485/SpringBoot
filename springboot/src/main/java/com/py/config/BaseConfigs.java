package com.py.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @作者: zgy
 * @描述: 这里做一些基础配置
 */
@Component
@ConfigurationProperties(prefix = "base-config")
public class BaseConfigs {
    //短信失效时间
    private int dxTimeout;

    public int getDxTimeout() {
        return dxTimeout;
    }

    public void setDxTimeout(int dxTimeout) {
        this.dxTimeout = dxTimeout;
    }

}
