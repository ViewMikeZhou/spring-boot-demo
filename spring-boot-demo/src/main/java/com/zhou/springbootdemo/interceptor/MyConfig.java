package com.zhou.springbootdemo.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class MyConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * addPathPatterns 添加拦截("/hello")拦截hello
         *                          "/**"   拦截所有
         *
         */
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/hello")
             //   .excludePathPatterns("/test")      不拦截 /test
        ;
        super.addInterceptors(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //http://localhost:8082/tologin  映射到 index.html
        registry.addViewController("/tologin").setViewName("index");
        super.addViewControllers(registry);
    }
}
