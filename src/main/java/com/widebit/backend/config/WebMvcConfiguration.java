package com.widebit.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new UserRoleAuthorizationInterceptor()).addPathPatterns("/pics/**");
        super.addInterceptors(registry);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:/home/lpp/image/");
        registry.addResourceHandler("/file/**").addResourceLocations("file:/home/lpp/file/");
        super.addResourceHandlers(registry);
    }
}
