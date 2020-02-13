package com.hs.course.controller;

import com.hs.course.dao.QuestionsMapper;
import com.hs.course.daogenerator.ChoiceGeneratorMapper;
import com.hs.course.daogenerator.QuestionsGeneratorMapper;
import com.hs.course.daogenerator.SummaryGeneratorMapper;
import com.hs.course.daogenerator.UserGeneratorMapper;
import com.hs.course.domaingenerator.ChoiceGenerator;
import com.hs.course.domaingenerator.QuestionsGenerator;
import com.hs.course.domaingenerator.QuestionsGeneratorExample;
import com.hs.course.domaingenerator.SummaryGenerator;
import com.hs.course.domaingenerator.UserGenerator;
import com.hs.course.domaingenerator.UserGeneratorExample;
import com.hs.course.entity.Result;
import com.hs.course.entity.User;
import com.hs.course.vo.QuestionsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 错题集
 */
@Controller
public class QuestionsController {
    @Autowired
    private UserGeneratorMapper userGeneratorMapper;
    @Autowired
    private ChoiceGeneratorMapper choiceGeneratorMapper;
    @Autowired
    private SummaryGeneratorMapper summaryGeneratorMapper;
    @Autowired
    private QuestionsMapper questionsMapper;
    @Autowired
    private QuestionsGeneratorMapper questionsGeneratorMapper;


    /**
     * 分页查询
     *
     * @return list + count
     */
    @RequestMapping("/questionsFenye")
    @ResponseBody
    public Result getQuestions(int pageNo, int pageSize, HttpSession session) {
        //获取当前用户id
        int userId = getUserId(session);
        //通过userId查询错题集中的相关错题list
        HashMap<Object, Object> map = new HashMap<>();
        map.put("pageNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        map.put("userId", userId);
        List<QuestionsGenerator> questionsGenerators = questionsMapper.selToFenye(map);
        //需要返回前端的list
        ArrayList<QuestionsVo> questionsVoList = new ArrayList<>();
        questionsGenerators.forEach(item -> {
            QuestionsVo questionsVo = new QuestionsVo();
            if (item.getType().equals("choice")) {
                //选择题部分
                ChoiceGenerator choiceGenerator = choiceGeneratorMapper.selectByPrimaryKey(item.getProblemid());
                questionsVo.setChapter(choiceGenerator.getChapter());
                questionsVo.setCourse(choiceGenerator.getCourse().equals("1") ? "计算机组成原理" : "数据结构");
                questionsVo.setTitle(choiceGenerator.getTitle());
            } else {
                //简答题部分
                SummaryGenerator summaryGenerator = summaryGeneratorMapper.selectByPrimaryKey(item.getProblemid());
                questionsVo.setChapter(summaryGenerator.getChapter());
                questionsVo.setCourse(summaryGenerator.getCourse().equals("1") ? "计算机组成原理" : "数据结构");
                questionsVo.setTitle(summaryGenerator.getTitle());
            }
            questionsVo.setId(item.getId());
            questionsVo.setUserid(userId);
            questionsVo.setProblemid(item.getProblemid());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String date = simpleDateFormat.format(item.getTime());
            questionsVo.setTime(date);
            questionsVo.setType(item.getType().equals("choice") ? "选择题" : "简答题");
            questionsVoList.add(questionsVo);
        });
        //查询总条数
        Integer count = questionsMapper.selCount(userId);
        HashMap<String, Object> vo = new HashMap<>();
        vo.put("data", questionsVoList);
        vo.put("count", count);

        return Result.builder()
                .data(vo)
                .code(1)
                .message("错题返回成功")
                .build();
    }

    /**
     * 通过题目类型和题目id定位该题
     * @param id  选择题或简答题的主键id
     * @param type  题目类型  简答题 or 选择题
     * @return 1
     */
    @RequestMapping("/deleteQuestions")
    @ResponseBody
    public Result deleteQuestions(int id, String type, HttpSession session) {
        int userId = getUserId(session);
        QuestionsGeneratorExample ex = new QuestionsGeneratorExample();
        ex.createCriteria()
                .andUseridEqualTo(userId)
                .andProblemidEqualTo(id)
                .andTypeEqualTo(type.equals("选择题")?"choice":"summary");
        questionsGeneratorMapper.deleteByExample(ex);
        return Result.builder()
                .message("删除成功")
                .code(1)
                .build();
    }

    /**
     * 通过session获取用户id
     *
     * @param session session
     * @return
     */
    private int getUserId(HttpSession session) {
        User user = (User) session.getAttribute("user");
        UserGeneratorExample example = new UserGeneratorExample();
        example.createCriteria()
                .andNameEqualTo(user.getName());
        List<UserGenerator> userGenerators = userGeneratorMapper.selectByExample(example);
        return userGenerators.get(0).getId();
    }
}
