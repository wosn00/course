package com.hs.course.controller;

import com.hs.course.courseService.AnswerMatching;
import com.hs.course.dao.ChoiceMapper;
import com.hs.course.dao.SummaryMapper;
import com.hs.course.daogenerator.ChoiceGeneratorMapper;
import com.hs.course.daogenerator.QuestionsGeneratorMapper;
import com.hs.course.daogenerator.SummaryGeneratorMapper;
import com.hs.course.daogenerator.UserGeneratorMapper;
import com.hs.course.domaingenerator.ChoiceGenerator;
import com.hs.course.domaingenerator.QuestionsGenerator;
import com.hs.course.domaingenerator.SummaryGenerator;
import com.hs.course.domaingenerator.UserGenerator;
import com.hs.course.domaingenerator.UserGeneratorExample;
import com.hs.course.entity.Choice;
import com.hs.course.entity.Result;
import com.hs.course.entity.Summary;
import com.hs.course.entity.User;
import com.hs.course.request.ChoiceQueryReq;
import com.hs.course.request.ExamQueryReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在线考试
 */
@Controller
public class ExamController {

    @Autowired
    private ChoiceMapper choiceMapper;
    @Autowired
    private SummaryMapper summaryMapper;
    @Autowired
    private ChoiceGeneratorMapper choiceGeneratorMapper;
    @Autowired
    private SummaryGeneratorMapper summaryGeneratorMapper;
    @Autowired
    private QuestionsGeneratorMapper questionsGeneratorMapper;
    @Autowired
    private UserGeneratorMapper userGeneratorMapper;
    @Autowired
    private AnswerMatching answerMatching;

    /**
     * @param course 课程
     * @return 题目
     */
    @RequestMapping("/exam_details")
    @ResponseBody
    public Object exam(String course) {
        Map<String, Object> query = new HashMap<>();
        int num = 20;
        query.put("num", num);
        HashMap<Object, Object> res = new HashMap<>();
        if (course.equals("1")) {
            query.put("course", course);
            List<Choice> choices = choiceMapper.selByRand(query);
            //为每条选择题加上从1开始的序号
            for (int i = 0; i < num; i++) {
                choices.get(i).setCountno(i + 1);
            }
            res.put("choices", choices);
            query.put("num", 2);
            List<Summary> summaries = summaryMapper.selByRand(query);
            //为每条简答题加上从1开始的序号
            for (int i = 0; i < 2; i++) {
                summaries.get(i).setCountno(i + 1);
            }
            res.put("summaries", summaries);
        } else {
            //TODO
            //添加数据结构课程的考试题
        }
        return res;
    }

    /**
     * 计算考试成绩
     *
     * @param examQueryReq 选择题list(20题) + 简答题list包装类(2题)
     * @return
     */
    @RequestMapping("/test_scores")
    @ResponseBody
    public Object testScores(@RequestBody ExamQueryReq examQueryReq, HttpSession session) {
        //通过userName查出userId
        User user = (User) session.getAttribute("user");
        UserGeneratorExample userGeneratorExample = new UserGeneratorExample();
        userGeneratorExample.createCriteria().andNameEqualTo(user.getName());
        List<UserGenerator> userGenerators = userGeneratorMapper.selectByExample(userGeneratorExample);
        int userId = userGenerators.get(0).getId();
        //分数
        int score = 0;
        //选择题正确题数
        int choicetag = 0;
        //简答题正确率
        float summarytag = 0f;
        //添加选择题分数
        for (ChoiceQueryReq choiceQueryReq : examQueryReq.getChoice()) {
            ChoiceGenerator choiceGenerator = choiceGeneratorMapper.selectByPrimaryKey(Integer.valueOf(choiceQueryReq.getId()));
            if (choiceGenerator.getAnswer().equals(choiceQueryReq.getAnswer())) {
                score += 4;
                choicetag++;
            } else {
                //进错题集
                QuestionsGenerator question = new QuestionsGenerator();
                question.setProblemid(Integer.valueOf(choiceQueryReq.getId()));
                question.setType("choice");
                question.setUserid(userId);
                try {
                    questionsGeneratorMapper.insertSelective(question);
                } catch (Exception e) {
                    //TODO
                    System.out.println("========Duplicate choice========");
                }
            }
        }
        //选择题正确率
        int choiceRate = choicetag * 5;
        for (ChoiceQueryReq choiceQueryReq : examQueryReq.getSummary()) {
            SummaryGenerator summaryGenerator = summaryGeneratorMapper.selectByPrimaryKey(Integer.valueOf(choiceQueryReq.getId().replace("summary", "")));
            Float matching = answerMatching.matching(choiceQueryReq.getAnswer(), summaryGenerator.getAnswer());
            score += matching * 10;
            summarytag += matching;
            //如果得分低于一半值，就进错题集
            if (matching < 0.5) {
                QuestionsGenerator question = new QuestionsGenerator();
                question.setProblemid(Integer.valueOf(choiceQueryReq.getId().replace("summary", "")));
                question.setType("summary");
                question.setUserid(userId);
                try {
                    questionsGeneratorMapper.insertSelective(question);
                } catch (Exception e) {
                    //TODO
                    System.out.println("=========Duplicate summary===========");
                }
            }

        }
        //简答题正确率
        DecimalFormat dF = new DecimalFormat("0");
        String summaryRate = dF.format( summarytag / 2 * 100);
        HashMap<Object, Object> map = new HashMap<>(16);
        map.put("score", score);
        map.put("choiceRate", choiceRate);
        map.put("summaryRate", summaryRate);
        return Result.builder()
                .message("成绩计算成功")
                .code(1)
                .data(map)
                .build();
    }
}
