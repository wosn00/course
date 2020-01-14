package com.hs.course.courseService;

import com.hs.course.dao.ChoiceMapper;
import com.hs.course.entity.Choice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChoiceService {

    @Autowired
    private ChoiceMapper choiceMapper;

    /**
     * @param pageNo   页码
     * @param pageSize 每页题数
     * @param course   课程
     * @param chapter  章节
     * @return 分页数据
     */
    public List<Choice> choiceFenye(int pageNo, int pageSize, int course, int chapter) {
        int pageId = (pageNo - 1) * pageSize;
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageId", pageId);
        map.put("pageSize", pageSize);
        map.put("course", course);
        map.put("chapter", chapter);
        return choiceMapper.selToFenye(map);
    }

    /**
     * @param course  课程
     * @param chapter 章节
     * @return 该课程下的章节的总题数
     */
    public int selCount(int course, int chapter) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("course", course);
        map.put("chapter", chapter);
        return choiceMapper.selCount(map);
    }

    public Choice selCountno(int course, int chapter, int id, int countno) {
        int count = selCount(course, chapter);
        Map<String, Integer> map = new HashMap<>();
        map.put("count", count);
        map.put("id", id);
        map.put("course", course);
        map.put("chapter", chapter);
        map.put("countno", countno);
        return choiceMapper.selCounntno(map);
    }


}
