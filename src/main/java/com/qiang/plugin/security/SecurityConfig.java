package com.qiang.plugin.security;

import helper.ConfigHelper;
import util.ReflectionUtil;

/**
 * 从配置文件中获取相关属性
 * Created by liq on 2018/4/25.
 */
public final class SecurityConfig {

    public static String getRealms(){
        return ConfigHelper.getString(SecurityConstant.REALMS);
    }

    public static MvcSecurity getMvcSecurity(){
        String className = ConfigHelper.getString(SecurityConstant.MVC_SECURITY);
        return (MvcSecurity) ReflectionUtil.newInstance(className);
    }

    public static String getJdbcAuthcQuery(){
        return ConfigHelper.getString(SecurityConstant.JDBC_AUTHC_QUERY);
    }

    public static String getJdbcRolesQuery(){
        return ConfigHelper.getString(SecurityConstant.JDBC_ROLES_QUERY);
    }

    public static String getJdbcPermissionsQuery(){
        return ConfigHelper.getString(SecurityConstant.JDBC_PERMISSIONS_QUERY);
    }

    public static boolean isCacheable(){
        return ConfigHelper.getBoolean(SecurityConstant.CHCHEABLE);
    }
}
