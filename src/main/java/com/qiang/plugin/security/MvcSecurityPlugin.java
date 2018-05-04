package com.qiang.plugin.security;

import org.apache.shiro.web.env.EnvironmentLoaderListener;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * Mvc Security插件
 * Created by liq on 2018/4/24.
 */
public class MvcSecurityPlugin implements ServletContainerInitializer{
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        //设置初始化参数
        servletContext.setInitParameter("shiroConfigLocations","classpath:mvc-security.ini");
        //注册Listener
        servletContext.addListener(EnvironmentLoaderListener.class);
        //注册Filter
        FilterRegistration.Dynamic mvcSecurityFilter = servletContext.addFilter("MvcSecurityFilter", MvcSecurityFilter.class);
        mvcSecurityFilter.addMappingForUrlPatterns(null, false,"/*");
    }
}
