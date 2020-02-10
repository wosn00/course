package com.hs.course.shiroconfig;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterMap = new LinkedHashMap<>();
        //静态文件
        filterMap.put("/css/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/images/**", "anon");
        filterMap.put("/font/**", "anon");
        filterMap.put("/layui/**", "anon");
        //页面
        filterMap.put("/", "anon");
        filterMap.put("/login", "anon");
        filterMap.put("/index", "anon");
        filterMap.put("/register/**","anon");
        filterMap.put("/error_occurred","anon");
        //角色授权的页面必须要anon
        filterMap.put("/edit_choice", "anon");
        filterMap.put("/edit_summary", "anon");
        filterMap.put("/updateChoice", "anon");
        filterMap.put("/updateSummary", "anon");
        filterMap.put("/accountFenye", "anon");
        filterMap.put("/back_system_edit_account", "anon");
        filterMap.put("/updateAccount", "anon");
        filterMap.put("/recordAnswerFenye", "anon");
        filterMap.put("/recordLoginFenye", "anon");
        filterMap.put("/edit_choice/**", "anon");
        filterMap.put("/back_system*", "anon");
        //认证
        filterMap.put("/**", "authc");
        //管理员权限
        filterMap.put("/edit_choice", "roles[1]");
        filterMap.put("/edit_summary", "roles[1]");
        filterMap.put("/updateChoice", "roles[1]");
        filterMap.put("/updateSummary", "roles[1]");
        filterMap.put("/accountFenye", "roles[1]");
        filterMap.put("/back_system_edit_account", "roles[1]");
        filterMap.put("/updateAccount", "roles[1]");
        filterMap.put("/recordAnswerFenye", "roles[1]");
        filterMap.put("/recordLoginFenye", "roles[1]");
        filterMap.put("/edit_choice/**", "roles[1]");
        filterMap.put("/back_system*", "roles[1]");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setLoginUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/index");
        return shiroFilterFactoryBean;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean(name = "userRealm")
    public UserRealm getRealm() {
        return new UserRealm();
    }

    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }
}