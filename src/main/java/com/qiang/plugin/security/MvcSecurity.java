package com.qiang.plugin.security;

import java.util.Set;

/**
 * MvcSecurity接口
 * <br/>
 * 可在应用中实现该接口，或者在smart.properties文件中提供以下基于SQL的配置项：
 * <ul>
 *     <li>plugin.security.jdbc.authc_query</li>根据用户名获取密码 如="SELECT password FROM user WHERE username = ?"
 *     <li>plugin.security.jdbc.roles_query</li>根据用户名获取角色名集合
 *     <li>plugin.security.jdbc.permissions_query</li>根据角色名获取权限名集合
 * </ul>
 * Created by liq on 2018/4/24.
 */
public interface MvcSecurity {
    /**
     * 根据用户名获取密码
     * @param username 用户名
     * @return 密码
     */
    String getPassword(String username);

    /**
     * 根据用户名获取角色名集合
     * @param username 用户名
     * @return 角色名集合
     */
    Set<String> getRoleNameSet(String username);

    /**
     * 根据角色名获取权限名集合
     * @param roleName 角色名
     * @return 权限名集合
     */
    Set<String> getPermissionNameSet(String roleName);
}
