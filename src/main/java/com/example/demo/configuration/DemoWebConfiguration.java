package com.example.demo.configuration;

import com.example.demo.interceptor.LoginRequiredInterceptor;
import com.example.demo.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by think on 2017/7/5.
 */
@Component
public class DemoWebConfiguration extends WebMvcConfigurerAdapter{

    @Autowired
    PassportInterceptor passportInterceptor;//看看用户是谁

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;//看看用户访问的页面是不是符合要求

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);//处理所有页面
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/setting*");
        //处理setting相关的页面
        super.addInterceptors(registry);
    }
}
