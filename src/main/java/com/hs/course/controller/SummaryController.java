package com.hs.course.controller;

import com.hs.course.courseService.AnswerMatching;
import com.hs.course.courseService.SummaryService;
import com.hs.course.daogenerator.SummaryGeneratorMapper;
import com.hs.course.daogenerator.TotalAnswerGeneratorMapper;
import com.hs.course.domaingenerator.SummaryGenerator;
import com.hs.course.domaingenerator.TotalAnswerGenerator;
import com.hs.course.entity.Result;
import com.hs.course.entity.Summary;
import com.hs.course.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简答题相关
 */
@Controller
public class SummaryController {
    @Autowired
    private SummaryService summaryService;
    @Autowired
    private AnswerMatching answerMatching;
    @Autowired
    private SummaryGeneratorMapper summaryGeneratorMapper;
    @Autowired
    private TotalAnswerGeneratorMapper totalAnswerGeneratorMapper;

    /**
     * session存章节，int型
     *
     * @param chapter 章节
     */
    @RequestMapping("/jizu_summary/{chapter}")
    public String summaryQuestion(@PathVariable("chapter") int chapter, HttpSession session) {
        session.setAttribute("jizuChapter", chapter);
        return "jizu_summary";
    }

    /**
     * 复用
     * 简答题分页查询
     *
     * @return 页数+简答题List
     */
    @RequestMapping("/summaryFenye")
    @ResponseBody
    public Map<String, Object> jizuChoiceQuestion(int pageNo, int pageSize, int course, int chapter) {
        int count = summaryService.selCount(course, chapter);
        List<Summary> choices = summaryService.summaryFenye(pageNo, pageSize, course, chapter);
        HashMap<String, Object> map = new HashMap<>();
        map.put("count", count);
        map.put("list", choices);
        return map;
    }

    /**
     * 简答题详情，通过题目id或题目countno查询
     *
     * @param id      题目id
     * @param countno 题目题号
     */
    @RequestMapping("/summary_detail/{course}/{chapter}/{id}")
    public String summaryDetail(@PathVariable("id") int id, @PathVariable("chapter") int chapter
            , @PathVariable("course") int course, @RequestParam(required = false) Integer countno, HttpSession session) {
        Summary summary;
        if (countno != null) {
            summary = summaryService.selCountno(course, chapter, -1, countno);
        } else {
            summary = summaryService.selCountno(course, chapter, id, -1);
        }
        session.setAttribute("summaryDetail", summary);
        return "summary_detail";
    }

    /**
     * @return 返回该章节的总题数
     */
    @RequestMapping("/summary_num")
    @ResponseBody
    public Integer choiceNum(int course, int chapter) {
        return summaryService.selCount(course, chapter);
    }

    /**
     * 判断简答题答案匹配度
     *
     * @param id     题目id
     * @param answer 答案
     * @return
     */
    @RequestMapping("/summaryJudge")
    @ResponseBody
    public Result<Map> summaryJudge(int id, String answer,HttpSession session) {
        //获得userName
        User user = (User) session.getAttribute("user");
        String userName=user.getName();
        SummaryGenerator summaryGenerator = summaryGeneratorMapper.selectByPrimaryKey(id);
        Map<Object, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(answer)) {
            map.put("matching", 0);
            map.put("standAnswer", summaryGenerator.getAnswer());
            return Result.<Map>builder()
                    .code(0)
                    .message("输入为空")
                    .data(map)
                    .build();
        }
        Float matching = answerMatching.matching(answer, summaryGenerator.getKeyword());
        map.put("matching", matching * 100);
        map.put("standAnswer", summaryGenerator.getAnswer());
        //存入答题数量表
        TotalAnswerGenerator totalAnswerGenerator = new TotalAnswerGenerator();
        totalAnswerGenerator.setUsername(userName);
        totalAnswerGenerator.setProblemid(id);
        Float v = matching * 100;
        totalAnswerGenerator.setResult(v.intValue());
        totalAnswerGenerator.setType("summary");
        totalAnswerGenerator.setCourse(1);
        try {
            totalAnswerGeneratorMapper.insertSelective(totalAnswerGenerator);
        } catch (Exception e) {
            //TODO
            System.out.println("=====出现重复题目，简答题======");
        }

        return Result.<Map>builder()
                .code(1)
                .message("响应成功")
                .data(map)
                .build();
    }
}
