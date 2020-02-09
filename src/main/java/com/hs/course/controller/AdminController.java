package com.hs.course.controller;

import com.hs.course.daogenerator.ChoiceGeneratorMapper;
import com.hs.course.daogenerator.SummaryGeneratorMapper;
import com.hs.course.domaingenerator.ChoiceGenerator;
import com.hs.course.domaingenerator.SummaryGenerator;
import com.hs.course.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController {
    @Autowired
    private ChoiceGeneratorMapper choiceGeneratorMapper;
    @Autowired
    private SummaryGeneratorMapper summaryGeneratorMapper;

    /**
     * 进入选择题编辑页
     * @param id 主键id
     */
    @RequestMapping("/edit_choice/{id}")
    public String editChoice(@PathVariable("id") int id,int chapter, HttpSession session){
        ChoiceGenerator choice = choiceGeneratorMapper.selectByPrimaryKey(id);
        session.setAttribute("editChoice",choice);
        session.setAttribute("backChapter",chapter);
        return "edit_choice";
    }
    /**
     * 进入简答题编辑页
     * @param id 主键id
     */
    @RequestMapping("/edit_summary/{id}")
    public String editSummary(@PathVariable("id") int id,int chapter, HttpSession session){
        SummaryGenerator summaryGenerator = summaryGeneratorMapper.selectByPrimaryKey(id);
        session.setAttribute("editSummary",summaryGenerator);
        session.setAttribute("backChapter",chapter);
        return "edit_summary";
    }
    /**
     * 更新选择题
     */
    @RequestMapping("/updateChoice")
    @ResponseBody
    public Object updateChoice(ChoiceGenerator choiceGenerator){
        choiceGeneratorMapper.updateByPrimaryKeySelective(choiceGenerator);
        return Result.builder()
                .code(1)
                .build();
    }

    /**
     * 更新简答题
     */
    @RequestMapping("/updateSummary")
    @ResponseBody
    public Object updateChoice(SummaryGenerator summaryGenerator){
        summaryGeneratorMapper.updateByPrimaryKeySelective(summaryGenerator);
        return Result.builder()
                .code(1)
                .build();
    }



}
