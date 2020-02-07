package com.hs.course.controller;

import com.hs.course.courseService.AccuracyRateService;
import com.hs.course.courseService.RangeCalculationService;
import com.hs.course.daogenerator.UserGeneratorMapper;
import com.hs.course.domaingenerator.UserGenerator;
import com.hs.course.domaingenerator.UserGeneratorExample;
import com.hs.course.entity.Result;
import com.hs.course.entity.User;
import com.hs.course.vo.AccuracyRateVo;
import com.hs.course.vo.PersonalVo;
import com.hs.course.vo.TotalCountVo;
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
    @Autowired
    private RangeCalculationService rangeCalculationService;
    @Autowired
    private AccuracyRateService accuracyRateService;

    /**
     * 返回个人信息
     */
    @GetMapping("get_information")
    @ResponseBody
    public Result getInformation(HttpSession session) {
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
    public Result updateInformation(String name, String className, String studentID) {
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

    /**
     * 计算对应科目已答题数，正确率
     */
    @GetMapping("get_range")
    @ResponseBody
    public Result getRange(String course, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String userName = user.getName();
        TotalCountVo vo = rangeCalculationService.calculation(userName, course);

        return Result.builder()
                .message("返回成功")
                .code(1)
                .data(vo)
                .build();
    }

    /**
     * 获取计组和数构的平均正确率
     *
     */
    @GetMapping("get_accuracy_rate")
    @ResponseBody
    public Object getAccuracyRate(HttpSession session){
        User user = (User) session.getAttribute("user");
        String userName = user.getName();
        AccuracyRateVo vo = accuracyRateService.getAccuracyRate(userName);

        return Result.builder()
                .code(1)
                .message("返回平均正确率成功")
                .data(vo)
                .build();
    }

}
