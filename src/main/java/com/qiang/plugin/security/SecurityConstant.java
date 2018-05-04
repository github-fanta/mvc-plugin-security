package com.qiang.plugin.security;

/**
 * 常量接口
 * Created by liq on 2018/4/25.
 */
public interface SecurityConstant {

    String REALMS ="mvc.plugin.security.realms";//需要用户配置，两个选项“jdbc”或者“custom”或者两者皆有（用“,”隔开）
    String REALMS_JDBC = "jdbc";
    String REALMS_CUSTOM = "custom";

    String MVC_SECURITY = "mvc.plugin.security.custom.class";//自定义实现MvcSecurity接口的类
    String JDBC_AUTHC_QUERY = "mvc.plugin.security.jdbc.authc_query";
    String JDBC_ROLES_QUERY = "mvc.plugin.security.jdbc.roles_query";
    String JDBC_PERMISSIONS_QUERY = "mvc.plugin.security.jdbc.permissions_query";
    String CHCHEABLE = "mvc.plugin.security.cache";
}
