package com.hs.course.controller;

import com.hs.course.courseService.ChoiceService;
import com.hs.course.daogenerator.ChoiceGeneratorMapper;
import com.hs.course.daogenerator.TotalAnswerGeneratorMapper;
import com.hs.course.domaingenerator.ChoiceGenerator;
import com.hs.course.domaingenerator.TotalAnswerGenerator;
import com.hs.course.entity.Choice;
import com.hs.course.entity.Result;
import com.hs.course.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择题相关
 *
 */
@Controller
public class CourseController {
    @Autowired
    private ChoiceService choiceService;
    @Autowired
    private ChoiceGeneratorMapper choiceGeneratorMapper;
    @Autowired
    private TotalAnswerGeneratorMapper totalAnswerGeneratorMapper;

    @RequestMapping("/{pageNme}")
    public String jump(@PathVariable String pageNme) {
        return pageNme;
    }

    /**
     * session存章节，int型
     * @param chapter 章节
     */
    @RequestMapping("/jizu_choicequestion/{chapter}")
    public String choiceQuestion(@PathVariable("chapter") int chapter, HttpSession session) {
        session.setAttribute("jizuChapter", chapter);
        return "jizu_choicequestion";
    }

    /**
     * 复用
     * 选择题分页查询
     * @return 页数+选择题List
     */
    @GetMapping("/choiceFenye")
    @ResponseBody
    public Map<String, Object> jizuChoiceQuestion(int pageNo, int pageSize, int course, int chapter) {
        int count = choiceService.selCount(course, chapter);
        List<Choice> choices = choiceService.choiceFenye(pageNo, pageSize, course, chapter);
        HashMap<String, Object> map = new HashMap<>();
        map.put("count", count);
        map.put("list", choices);
        return map;
    }

    /**
     * 复用
     * 选择题详情，通过题目id或题目countno查询
     * @param id 题目id
     * @param countno 题目题号
     */
    @RequestMapping("/choice_detail/{course}/{chapter}/{id}")
    public String choiceDetail(@PathVariable("id") int id,@PathVariable("chapter") int chapter
            ,@PathVariable("course") int course,@RequestParam(required = false) Integer countno, HttpSession session) {
        Choice choice;
        if (countno != null){
             choice = choiceService.selCountno(course,chapter,-1,countno);
        }else {
             choice = choiceService.selCountno(course, chapter, id, -1);
        }
        session.setAttribute("choiceDetail",choice);
        return "choice_detail";
    }

    /**
     *
     * @return 返回该章节的总题数
     */
    @RequestMapping("/choice_num")
    @ResponseBody
    public Integer choiceNum(int course,int chapter) {
         return choiceService.selCount(course, chapter);
    }

    /**
     * @return 选择题答案对错 "0" 错误 "1" 正确 + 正确答案
     */
    @RequestMapping("/answerJudge")
    @ResponseBody
    public Result<Map> choiceAnswerJudge(Integer id, String answer, HttpSession session) {
        //获取userName
        User user= (User) session.getAttribute("user");
        String userName=user.getName();

        ChoiceGenerator choice = choiceGeneratorMapper.selectByPrimaryKey(id);
        session.setAttribute("choiceDetail",choice);
        Map<String, String> map = new HashMap<>(16);
        map.put("answer",choice.getAnswer());
        //analysis可以为空
        if (StringUtils.isNotBlank(choice.getAnalysis())) {
            map.put("analysis", choice.getAnalysis());
        }
        //添加做题总数表
        TotalAnswerGenerator totalAnswerGenerator = new TotalAnswerGenerator();
        totalAnswerGenerator.setCourse(1);
        totalAnswerGenerator.setType("choice");
        totalAnswerGenerator.setProblemid(id);
        totalAnswerGenerator.setUsername(userName);
        //判断答案是否正确
        if (choice.getAnswer().equals(answer.trim())){
            totalAnswerGenerator.setResult(1);
            try {
                totalAnswerGeneratorMapper.insertSelective(totalAnswerGenerator);
            } catch (Exception e) {
                //TODO
                System.out.println("==========出现重复做题（正确）==========");
            }
            return Result.<Map>builder()
                    .code(1)
                    .message("回答正确")
                    .data(map)
                    .build();
        }else {
            totalAnswerGenerator.setResult(0);
            try {
                totalAnswerGeneratorMapper.insertSelective(totalAnswerGenerator);
            } catch (Exception e) {
                //TODO
                System.out.println("==========出现重复做题（错误）==========");
            }
            return Result.<Map>builder()
                    .code(0)
                    .message("回答错误")
                    .data(map)
                    .build();
        }
    }
}
