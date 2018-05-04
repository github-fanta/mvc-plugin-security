package com.qiang.plugin.security.password;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import util.CodecUtil;

import java.util.Arrays;

/**
 * Created by liq on 2018/4/25.
 */
public class MD5CredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //表单提交过来的密码明文，尚未通过MD5加密
        String submitted = String.valueOf(((UsernamePasswordToken) token).getPassword());
        //获取数据库中存储的密码，已通过MD5加密
        String encrypted = String.valueOf(info.getCredentials());//info.getCredentials()
        return Arrays.equals(CodecUtil.md5(submitted).toCharArray(), (char[]) info.getCredentials());
    }
}
