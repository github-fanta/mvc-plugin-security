package com.qiang.plugin.security.realm;

import com.qiang.plugin.security.MvcSecurity;
import com.qiang.plugin.security.SecurityConstant;
import com.qiang.plugin.security.password.MD5CredentialsMatcher;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * 基于Mvc 的自定义Realm(需要实现MvcSecurity接口)
 * Created by liq on 2018/4/25.
 */
public class MvcCustomRealm extends AuthorizingRealm {

    private final MvcSecurity mvcSecurity;

    public MvcCustomRealm(MvcSecurity mvcSecurity) {
        this.mvcSecurity = mvcSecurity;
        super.setName(SecurityConstant.REALMS_CUSTOM);
        //使用MD5加密算法
        super.setCredentialsMatcher(new MD5CredentialsMatcher());
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (token == null){
            throw new AuthenticationException("parameter token is null");
        }
        //通过AuthenticationToken 对象获取从表单中提交过来的用户名
        String username = ((UsernamePasswordToken) token).getUsername();
        //通过 MvcSecurity 接口并根据用户名获取数据库中存放的密码
        String password = mvcSecurity.getPassword(username);

        //将用户名与密码放入AuthenticationInfo 对象中，便于后续的认证操作
        //SimpleAuthenticationInfo对象用于提供给SecurityManager来创建Subject（提供身份信息）
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
        //若在Shiro中配置多个Realm，身份信息可能就有多个；因此其提供了PrincipalCollection用于聚合这些身份信息
        //MutablePrincipalCollection是一个可变的PrincipalConllection接口，目前Shiro只提供了一个实现SimplePrincipalCollection
        //如“user1, custom”,"user1, jdbc"
        authenticationInfo.setPrincipals(new SimplePrincipalCollection(username, super.getName()));
        authenticationInfo.setCredentials(password);
        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null){
            throw new AuthorizationException("parameter principals is null");
        }
        //获取已认证用户的用户名
        String username = (String) super.getAvailablePrincipal(principals);
        //通过MvcSecurity接口并根据用户名获取角色集合
        Set<String> roleNameSet = mvcSecurity.getRoleNameSet(username);
        //通过MvcSecurity接口并根据角色名获取与其对应的权限名集合
        Set<String> permissionNameSet = new HashSet<String>();
        if (roleNameSet != null && roleNameSet.size() > 0){
            for(String roleName : roleNameSet){
                Set<String> currentPermissionNameSet = mvcSecurity.getPermissionNameSet(roleName);
                permissionNameSet.addAll(currentPermissionNameSet);
            }
        }

        //将角色名集合与权限名集合放入AuthorizationInfo对象中，便于后续的授权操作
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roleNameSet);
        authorizationInfo.setStringPermissions(permissionNameSet);
        return authorizationInfo;
    }
}
