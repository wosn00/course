package com.hs.course.controller;

import com.hs.course.dao.ChoiceMapper;
import com.hs.course.dao.SummaryMapper;
import com.hs.course.entity.Choice;
import com.hs.course.entity.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private ChoiceMapper choiceMapper;
    @Autowired
    private SummaryMapper summaryMapper;
    private static Logger logger = LoggerFactory.getLogger(SearchController.class);

    @RequestMapping("/fuzzySearch")
    public String fuzzySearch(String searchText, String type, HttpSession session) {
        logger.info("模糊搜索，关键词：{}",searchText);
        session.setAttribute("searchText", searchText);
        session.setAttribute("searchType", type);
        return "fuzzySearch";
    }

    @RequestMapping("/fuzzySearchFenye")
    @ResponseBody
    public Object fuzzySearchFenye(int pageNo, int pageSize, HttpSession session) {
        String searchText = (String) session.getAttribute("searchText");
        String searchType = (String) session.getAttribute("searchType");
        int count;
        int pageId = (pageNo - 1) * pageSize;
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageId",pageId);
        map.put("pageSize",pageSize);
        map.put("searchText",searchText);
        HashMap<String, Object> result = new HashMap<>();
        if (searchType.equals("choice")) {
            count = choiceMapper.selFuzzyCount(searchText);
            List<Choice> choices = choiceMapper.selFuzzyFenye(map);
            result.put("count",count);
            result.put("list",choices);
            result.put("type","choice");
            return result;
        } else {
            count = summaryMapper.selFuzzyCount(searchText);
            List<Summary> choices = summaryMapper.selFuzzyFenye(map);
            result.put("count",count);
            result.put("list",choices);
            result.put("type","summary");
            return result;
        }
    }


}
