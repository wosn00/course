package com.hs.course.controller;

import com.hs.course.courseService.ChoiceService;
import com.hs.course.entity.Choice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数构部分
 */
@Controller
public class ShugouCourseController {


    /**
     * session存章节，int型
     * @param chapter 章节
     */
    @RequestMapping("/shugou_choicequestion/{chapter}")
    public String choiceQuestion(@PathVariable("chapter") int chapter, HttpSession session) {
        session.setAttribute("shugouChapter", chapter);
        return "shugou_choicequestion";
    }
    /**
     * session存章节，int型
     *
     * @param chapter 章节
     */
    @RequestMapping("/shugou_summary/{chapter}")
    public String summaryQuestion(@PathVariable("chapter") int chapter, HttpSession session) {
        session.setAttribute("shugouChapter", chapter);
        return "shugou_summary";
    }

}
