package com.hs.course.controller;

import com.hs.course.dao.UserMapper;
import com.hs.course.daogenerator.UserGeneratorMapper;
import com.hs.course.domaingenerator.UserGenerator;
import com.hs.course.entity.Result;
import com.hs.course.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@Controller
public class BackSystemController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserGeneratorMapper userGeneratorMapper;

    @GetMapping("accountFenye")
    @ResponseBody
    public Object accountFenye(int pageNo, int pageSize){
        int pageId = (pageNo - 1) * pageSize;
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageId", pageId);
        map.put("pageSize", pageSize);
        List<User> users = userMapper.selFenye(map);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int count = userGeneratorMapper.countByExample(null);
        HashMap<String, Object> vo = new HashMap<>();
        vo.put("count", count);
        vo.put("list", users);
        return vo;
    }

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
        userGeneratorMapper.updateByPrimaryKeySelective(userGenerator);
        return Result.builder()
                .code(1)
                .build();
    }
}
