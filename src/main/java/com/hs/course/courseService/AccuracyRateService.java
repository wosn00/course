package com.hs.course.courseService;

import com.hs.course.dao.TotalAnswerMapper;
import com.hs.course.vo.AccuracyRateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AccuracyRateService {

    @Autowired
    private TotalAnswerMapper totalAnswerMapper;

    /**
     * 获取平均正确率
     * @return
     */
    public AccuracyRateVo getAccuracyRate(String userName){

        HashMap<String, Object> map = new HashMap<>();
        map.put("userName",userName);
        map.put("course",1);
        map.put("type","choice");
        Float jizuChoiceRate = totalAnswerMapper.selAvg(map);
        map.put("type","summary");
        Float jizuSummaryRate = totalAnswerMapper.selAvg(map);
        map.put("course",2);
        map.put("type","choice");
        Float shugouChoiceRate = totalAnswerMapper.selAvg(map);
        map.put("type","summary");
        Float shugouSummaryRate = totalAnswerMapper.selAvg(map);
        AccuracyRateVo vo = new AccuracyRateVo();
        vo.setJizuChoiceRate(jizuChoiceRate.intValue());
        vo.setJizuSummaryRate(jizuSummaryRate.intValue());
        vo.setShugouChoiceRate(shugouChoiceRate.intValue());
        vo.setShugouSummaryRate(shugouSummaryRate.intValue());
        return vo;


    }

}
