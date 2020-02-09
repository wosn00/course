package com.hs.course.controller;

import com.hs.course.entity.Status;
import com.hs.course.entity.User;
import com.hs.course.stateEnum.LoginState;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    @ResponseBody
    public Status login(String name, String pwd, HttpSession session, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);
        Status status = new Status();
        User user = new User();
        try {
            subject.login(token);
            //用户信息存进session
            user.setName(name);
            user.setPwd(pwd);
            user.setRole(2);
            session.setAttribute("user", user);
            status.setCode(LoginState.HTTP_200.getCode());
            status.setMsg(LoginState.HTTP_200.getMsg());
            return status;
        } catch (UnknownAccountException e) {
            session.setAttribute("msg", "用户名不存在");
            status.setCode(LoginState.HTTP_206.getCode());
            status.setMsg(LoginState.HTTP_206.getMsg());
            return status;

        } catch (IncorrectCredentialsException e) {
            session.setAttribute("msg", "密码错误");
            status.setCode(LoginState.HTTP_207.getCode());
            status.setMsg(LoginState.HTTP_207.getMsg());
            return status;
        }
    }

}
