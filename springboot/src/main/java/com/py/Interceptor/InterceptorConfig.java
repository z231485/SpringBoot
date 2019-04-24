package com.py.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc配置类
 */
@SpringBootConfiguration
public class InterceptorConfig implements WebMvcConfigurer {
   // @Autowired
  //  LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //     添加 loginInterceptor 到拦截器
        // InterceptorRegistration interceptorRegistration = registry.addInterceptor(loginInterceptor);
        // 拦截地址配置
        // interceptorRegistration.addPathPatterns("/**");
        //不拦截的地址配置
        // interceptorRegistration.excludePathPatterns();
    }
}
