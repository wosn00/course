package com.hs.course.controller;

import com.hs.course.courseService.ChoiceService;
import com.hs.course.entity.Choice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(ShugouCourseController.class);


    /**
     * session存章节，int型
     * @param chapter 章节
     */
    @RequestMapping("/shugou_choicequestion/{chapter}")
    public String choiceQuestion(@PathVariable("chapter") int chapter, HttpSession session) {
        logger.info("进入数据结构选择题，章节：{}",chapter);
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
        logger.info("进入数据结构简答题，章节：{}",chapter);
        session.setAttribute("shugouChapter", chapter);
        return "shugou_summary";
    }

}
