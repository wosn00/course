package com.hs.course.controller;

import com.hs.course.dao.RecordAnswerMapper;
import com.hs.course.dao.RecordLoginMapper;
import com.hs.course.dao.UserMapper;
import com.hs.course.daogenerator.RecordAnswerGeneratorMapper;
import com.hs.course.daogenerator.RecordLoginGeneratorMapper;
import com.hs.course.daogenerator.UserGeneratorMapper;
import com.hs.course.domaingenerator.RecordAnswerGenerator;
import com.hs.course.domaingenerator.RecordLoginGenerator;
import com.hs.course.domaingenerator.UserGenerator;
import com.hs.course.entity.Result;
import com.hs.course.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
public class BackSystemController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserGeneratorMapper userGeneratorMapper;
    @Autowired
    private RecordAnswerGeneratorMapper recordAnswerGeneratorMapper;
    @Autowired
    private RecordAnswerMapper recordAnswerMapper;
    @Autowired
    private RecordLoginGeneratorMapper recordLoginGeneratorMapper;
    @Autowired
    private RecordLoginMapper recordLoginMapper;
    private static Logger logger = LoggerFactory.getLogger(BackSystemController.class);

    @GetMapping("/accountFenye")
    @ResponseBody
    public Object accountFenye(int pageNo, int pageSize){
        int pageId = (pageNo - 1) * pageSize;
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageId", pageId);
        map.put("pageSize", pageSize);
        List<User> users = userMapper.selFenye(map);
        int count = userGeneratorMapper.countByExample(null);
        HashMap<String, Object> vo = new HashMap<>();
        vo.put("count", count);
        vo.put("list", users);
        return vo;
    }

    /**
     * 账号编辑页
     * @param id 账号id
     */
    @GetMapping("/back_system_edit_account/{id}")
    public Object editAccount(@PathVariable("id") int id, HttpSession session){
        UserGenerator user = userGeneratorMapper.selectByPrimaryKey(id);
        session.setAttribute("editAccount",user);
        return "back_system_edit_account";
    }

    /**
     * 更新账号信息
     */
    @PostMapping("/updateAccount")
    @ResponseBody
    public Result updateAccount(UserGenerator userGenerator){
        logger.info("更新账号信息：{}",userGenerator);
        userGeneratorMapper.updateByPrimaryKeySelective(userGenerator);
        return Result.builder()
                .code(1)
                .build();
    }
    /**
     * 答题日志分页
     */
    @GetMapping("/recordAnswerFenye")
    @ResponseBody
    public Object recordAnswerFenye(int pageNo, int pageSize){
        int pageId = (pageNo - 1) * pageSize;
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageId", pageId);
        map.put("pageSize", pageSize);
        List<RecordAnswerGenerator> recordAnswer = recordAnswerMapper.selFenye(map);
        int count = recordAnswerGeneratorMapper.countByExample(null);
        HashMap<String, Object> vo = new HashMap<>();
        vo.put("count", count);
        vo.put("list", recordAnswer);
        return vo;
    }
    /**
     * 登录日志分页
     */
    @GetMapping("/recordLoginFenye")
    @ResponseBody
    public Object recordLoginFenye(int pageNo, int pageSize){
        int pageId = (pageNo - 1) * pageSize;
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageId", pageId);
        map.put("pageSize", pageSize);
        List<RecordLoginGenerator> recordLogin = recordLoginMapper.selFenye(map);
        int count = recordLoginGeneratorMapper.countByExample(null);
        HashMap<String, Object> vo = new HashMap<>();
        vo.put("count", count);
        vo.put("list", recordLogin);
        return vo;
    }


}
