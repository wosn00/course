package com.hs.course.loginService;

import com.hs.course.dao.UserMapper;
import com.hs.course.entity.Status;
import com.hs.course.entity.User;
import com.hs.course.stateEnum.LoginState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class ResetJudgeService {
    @Autowired
    private UserMapper userMapper;
    private static Logger logger = LoggerFactory.getLogger(ResetJudgeService.class);

    public Status resetJudge(String name, String pwd, String phone, String code, HttpSession session) {
        String phoneMessage = (String) session.getAttribute("phoneMessage");
        User byPhone = userMapper.findByPhone(phone);
        User byName = userMapper.findByName(name);
        if (phoneMessage == null) {
            Status status = new Status(LoginState.HTTP_205.getCode(), LoginState.HTTP_205.getMsg());
            logger.info("重置密码失败，{}",LoginState.HTTP_205.getMsg());
            return status;
        } else if (!phoneMessage.equals(code)) {
            Status status = new Status(LoginState.HTTP_202.getCode(), LoginState.HTTP_202.getMsg());
            logger.info("重置密码失败，{}",LoginState.HTTP_202.getMsg());
            return status;
        } else if (byPhone == null) {
            Status status = new Status(LoginState.HTTP_209.getCode(), LoginState.HTTP_209.getMsg());
            logger.info("重置密码失败，{}",LoginState.HTTP_209.getMsg());
            return status;
        } else if (pwd.length() < 6 | pwd.length() > 20) {
            Status status = new Status(LoginState.HTTP_201.getCode(), LoginState.HTTP_201.getMsg());
            logger.info("重置密码失败，{}",LoginState.HTTP_201.getMsg());
            return status;
        } else if (name.length() < 6 | name.length() > 20) {
            Status status = new Status(LoginState.HTTP_204.getCode(), LoginState.HTTP_204.getMsg());
            logger.info("重置密码失败，{}",LoginState.HTTP_204.getMsg());
            return status;
        }else if (byName != null){
            Status status = new Status(LoginState.HTTP_210.getCode(), LoginState.HTTP_210.getMsg());
            logger.info("重置密码失败，{}",LoginState.HTTP_210.getMsg());
            return status;
        }
        User user = new User(name, pwd, phone, 2);
        userMapper.updateByPhone(user);
        Status status = new Status(LoginState.HTTP_200.getCode(), LoginState.HTTP_200.getMsg());
        logger.info("重置密码成功！，重置后信息：{}",user);
        return status;
    }
}

