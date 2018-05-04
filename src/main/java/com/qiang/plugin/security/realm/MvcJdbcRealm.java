package com.qiang.plugin.security.realm;

import com.qiang.plugin.security.SecurityConfig;
import com.qiang.plugin.security.password.MD5CredentialsMatcher;
import helper.DatabaseHelper;
import org.apache.shiro.realm.jdbc.JdbcRealm;

/**
 * 基于Mvc的JDBC Realm（需要提供mvc.plugin.security.jdbc.* 配置项）
 * Created by liq on 2018/4/25.
 */
public class MvcJdbcRealm  extends JdbcRealm{
    public MvcJdbcRealm(){
        super.setDataSource(DatabaseHelper.getDataSource());
        super.setAuthenticationQuery(SecurityConfig.getJdbcAuthcQuery());
        super.setUserRolesQuery(SecurityConfig.getJdbcRolesQuery());
        super.setPermissionsQuery(SecurityConfig.getJdbcPermissionsQuery());
        //开启后可以连接permission表进行查询
        super.setPermissionsLookupEnabled(false);
        //使用MD5加密
        super.setCredentialsMatcher(new MD5CredentialsMatcher());
    }
}
