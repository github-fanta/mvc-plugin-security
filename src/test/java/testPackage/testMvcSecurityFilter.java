package testPackage;

import com.qiang.plugin.security.SecurityConfig;
import com.qiang.plugin.security.password.MD5CredentialsMatcher;
import helper.DatabaseHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.util.JdbcUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.DigestUtils;
import sun.security.provider.MD5;
import util.CodecUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by liq on 2018/5/3.
 */
public class testMvcSecurityFilter {

    @Test
    public void setRealmsTest(){
        String jdbcAuthcQuery = SecurityConfig.getJdbcAuthcQuery();
        Assert.assertEquals("SELECT password FROM user WHERE username = ?",jdbcAuthcQuery);
    }

    @Test
    public void getMd5Test(){
        String ss = "123";
        String s = CodecUtil.md5(ss);
        Assert.assertEquals("202cb962ac59075b964b07152d234b70", s);
    }

    @Test
    public void getPasswordTest(){

        /*User user = DatabaseHelper.getEntity(User.class, "select password from user where username='aaa'");
        //Assert.assertEquals("20", user.getPassword());
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
        info.setCredentials(user.getPassword());
        Assert.assertTrue((CodecUtil.md5("123")).equals(String.valueOf(info.getCredentials())));*/
    }

}
