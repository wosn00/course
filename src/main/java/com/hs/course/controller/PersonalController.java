package com.hs.course.controller;

import com.hs.course.daogenerator.UserGeneratorMapper;
import com.hs.course.domaingenerator.UserGenerator;
import com.hs.course.domaingenerator.UserGeneratorExample;
import com.hs.course.entity.Result;
import com.hs.course.entity.User;
import com.hs.course.vo.PersonalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 个人中心
 */
@Controller
public class PersonalController {
    @Autowired
    private UserGeneratorMapper userGeneratorMapper;

    /**
     * 返回个人信息
     */
    @GetMapping("get_information")
    @ResponseBody
    public Object getInformation(HttpSession session) {
        //获得个人信息
        User user = (User) session.getAttribute("user");
        UserGeneratorExample example = new UserGeneratorExample();
        example.createCriteria().andNameEqualTo(user.getName());
        List<UserGenerator> userGenerators = userGeneratorMapper.selectByExample(example);
        List<PersonalVo> collect = userGenerators.stream()
                .map(PersonalVo::of)
                .collect(Collectors.toList());


        return Result.builder()
                .code(1)
                .message("返回个人信息成功")
                .data(collect.get(0))
                .build();
    }

    /**
     * 更新个人信息
     */
    @GetMapping("update_information")
    @ResponseBody
    public Object updateInformation(String name, String className, String studentID) {
        UserGeneratorExample ex = new UserGeneratorExample();
        ex.createCriteria().andNameEqualTo(name);
        UserGenerator user = new UserGenerator();
        user.setClassname(className);
        user.setStudentid(studentID);
        userGeneratorMapper.updateByExampleSelective(user, ex);
        return Result.builder()
                .code(1)
                .message("更新个人信息成功")
                .build();
    }


}
