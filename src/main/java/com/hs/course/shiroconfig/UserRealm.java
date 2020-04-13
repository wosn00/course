package com.hs.course.shiroconfig;

import com.hs.course.dao.UserMapper;
import com.hs.course.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //得到当前登录用户User
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //根据得到的用户信息去数据库查出权限
        int role = userMapper.findRoleByName(user.getName());
        //权限添加到shiro中
        info.addRole(String.valueOf(role));
//        System.out.println("=====================执行授权逻辑+=======================");
        return info;
    }


    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //通过token得到当前登录用户的信息，再去数据库查询真正的信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //通过数据库查询正确的登录用户信息
        User byName = userMapper.findByName(token.getUsername());
        if (byName == null) {
            return null;
        }
        //返回的这个对象是数据库查出来的正确信息，交给shiro自动比较
        return new SimpleAuthenticationInfo(byName, byName.getPwd(), "");
    }
}
