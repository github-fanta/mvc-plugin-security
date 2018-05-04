package com.qiang.plugin.security;

import com.qiang.plugin.security.realm.MvcCustomRealm;
import com.qiang.plugin.security.realm.MvcJdbcRealm;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 安全过滤器
 * Created by liq on 2018/4/24.
 */
public class MvcSecurityFilter extends ShiroFilter{
    @Override
    public void init() throws Exception {
        super.init();
        WebSecurityManager webSecurityManager = super.getSecurityManager();
        //给webSecurityManager设置Realm,可同时支持多个Realm,并按照先后顺序用逗号分割
        setRealms(webSecurityManager);
        //设置Cache,用于减少数据库查询次数，降低I/O访问
        setCache(webSecurityManager);
    }

    private void setRealms(WebSecurityManager webSecurityManager) {
        //读取mvc.plugin.security.realms配置项
        String securityRealms = SecurityConfig.getRealms();
        if (securityRealms != null){
            //根据逗号进行拆分
            String[] securityRealmArray = securityRealms.split(",");
            if (securityRealmArray.length > 0){
                //使realm具备唯一性与顺序性
                Set<Realm> realms = new LinkedHashSet<Realm>();
                for(String securityRealm : securityRealmArray){
                    if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_JDBC)){
                        //添加基于JDBC的Realm，需配置相关SQL查询语句
                        addJdbcRealm(realms);
                    }else if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_CUSTOM)){
                        //添加基于定制化的Realm,需实现MvcSecurity接口
                        addCustomRealm(realms);
                    }
                }
                RealmSecurityManager realmSecurityManager = (RealmSecurityManager) webSecurityManager;
                realmSecurityManager.setRealms(realms);
            }
        }
    }

    private void addJdbcRealm(Set<Realm> realms){
        MvcJdbcRealm mvcJdbcRealm = new MvcJdbcRealm();
        realms.add(mvcJdbcRealm);
    }

    private void addCustomRealm(Set<Realm> realms){
        //读取用户自定义的安全配置,在com.qiang.plugin.security.custom.class中
        MvcSecurity mvcSecurity = SecurityConfig.getMvcSecurity();
        //添加自己实现的Realm
        MvcCustomRealm mvcCustomRealm = new MvcCustomRealm(mvcSecurity);
        realms.add(mvcCustomRealm);
    }

    private void setCache(WebSecurityManager webSecurityManager) {
        //读取mvc.plugin.security.cache配置项
        if (SecurityConfig.isCacheable()){
            CachingSecurityManager cachingSecurityManager = (CachingSecurityManager) webSecurityManager;
            //使用基于内存的CacheManager
            MemoryConstrainedCacheManager cacheManager = new MemoryConstrainedCacheManager();
            cachingSecurityManager.setCacheManager(cacheManager);
        }
    }
}
