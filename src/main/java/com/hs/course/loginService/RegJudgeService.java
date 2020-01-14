package com.hs.course.loginService;

import com.hs.course.dao.UserMapper;
import com.hs.course.entity.Status;
import com.hs.course.entity.User;
import com.hs.course.stateEnum.LoginState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class RegJudgeService {
    @Autowired
    private UserMapper userMapper;

    public Status regJudge(String name, String pwd, String phone, String code, HttpSession session) {
        String phoneMessage = (String) session.getAttribute("phoneMessage");
        User findName = userMapper.findByName(name);
        if (phoneMessage == null) {
            Status status = new Status(LoginState.HTTP_205.getCode(), LoginState.HTTP_205.getMsg());
            return status;
        } else if (!phoneMessage.equals(code)) {
            Status status = new Status(LoginState.HTTP_202.getCode(), LoginState.HTTP_202.getMsg());
            return status;
        } else if (findName != null) {
            Status status = new Status(LoginState.HTTP_203.getCode(), LoginState.HTTP_203.getMsg());
            return status;
        } else if (pwd.length() < 6 | pwd.length() > 20) {
            Status status = new Status(LoginState.HTTP_201.getCode(), LoginState.HTTP_201.getMsg());
            return status;
        } else if (name.length() < 6 | name.length() > 20) {
            Status status = new Status(LoginState.HTTP_204.getCode(), LoginState.HTTP_204.getMsg());
            return status;
        } else if (userMapper.findByPhone(phone) != null) {
            Status status = new Status(LoginState.HTTP_208.getCode(), LoginState.HTTP_208.getMsg());
            return status;
        }
        User user = new User(name, pwd, phone, 2);
        userMapper.regSaveUser(user);
        Status status = new Status(LoginState.HTTP_200.getCode(), LoginState.HTTP_200.getMsg());

        return status;
    }
}
