package com.hs.course.controller;

import com.hs.course.entity.Status;
import com.hs.course.entity.User;
import com.hs.course.stateEnum.LoginState;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/login")
    @ResponseBody
    public Status login(String name, String pwd, HttpSession session, HttpServletRequest request) {
        logger.info("用户登录：用户名：{}，密码{}",name,pwd);
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
            logger.info("用户登录成功,信息：{}",user);
            return status;
        } catch (UnknownAccountException e) {
            logger.info("用户登录失败,用户名不存在");
            session.setAttribute("msg", "用户名不存在");
            status.setCode(LoginState.HTTP_206.getCode());
            status.setMsg(LoginState.HTTP_206.getMsg());
            return status;

        } catch (IncorrectCredentialsException e) {
            logger.info("用户登录失败,密码错误");
            session.setAttribute("msg", "密码错误");
            status.setCode(LoginState.HTTP_207.getCode());
            status.setMsg(LoginState.HTTP_207.getMsg());
            return status;
        }
    }

}
