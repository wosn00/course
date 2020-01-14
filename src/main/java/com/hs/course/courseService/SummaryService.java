package com.hs.course.courseService;

import com.hs.course.dao.SummaryMapper;
import com.hs.course.entity.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SummaryService {

    @Autowired
    private SummaryMapper summaryMapper;

    /**
     * @param pageNo   页码
     * @param pageSize 每页题数
     * @param course   课程
     * @param chapter  章节
     * @return 分页数据
     */
    public List<Summary> summaryFenye(int pageNo, int pageSize, int course, int chapter) {
        int pageId = (pageNo - 1) * pageSize;
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageId", pageId);
        map.put("pageSize", pageSize);
        map.put("course", course);
        map.put("chapter", chapter);
        return summaryMapper.selToFenye(map);
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
        return summaryMapper.selCount(map);
    }

    public Summary selCountno(int course, int chapter, int id, int countno) {
        int count = selCount(course, chapter);
        Map<String, Integer> map = new HashMap<>();
        map.put("count", count);
        map.put("id", id);
        map.put("course", course);
        map.put("chapter", chapter);
        map.put("countno", countno);
        return summaryMapper.selCounntno(map);
    }


}
