package com.hs.course.courseService;

import com.hs.course.daogenerator.ChoiceGeneratorMapper;
import com.hs.course.daogenerator.SummaryGeneratorMapper;
import com.hs.course.daogenerator.TotalAnswerGeneratorMapper;
import com.hs.course.domaingenerator.ChoiceGeneratorExample;
import com.hs.course.domaingenerator.SummaryGeneratorExample;
import com.hs.course.domaingenerator.TotalAnswerGeneratorExample;
import com.hs.course.utils.DataConversion;
import com.hs.course.vo.TotalCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RangeCalculationService {
    @Autowired
    private ChoiceGeneratorMapper choiceGeneratorMapper;
    @Autowired
    private SummaryGeneratorMapper summaryGeneratorMapper;
    @Autowired
    private TotalAnswerGeneratorMapper totalAnswerGeneratorMapper;

    /**
     *
     * @param userName 用户名
     * @param course 课程  1计组  2数构
     * @return 计算值
     */
    public TotalCountVo calculation(String userName, String course) {
        //计组选择题总数
        int jizuChoiceCount = 0;
        //数构选择题总数
        int shugouChoiceCount = 0;
        //计组简答题总数
        int jizuSummaryCount = 0;
        //数构简答题总数
        int shugouSummaryCount = 0;
        ChoiceGeneratorExample ex = new ChoiceGeneratorExample();
        SummaryGeneratorExample ex1 = new SummaryGeneratorExample();
        if (course.equals("1")) {
            ex.createCriteria().andCourseEqualTo("1");
            ex1.createCriteria().andCourseEqualTo("1");
            jizuChoiceCount = choiceGeneratorMapper.countByExample(ex);
            jizuSummaryCount = summaryGeneratorMapper.countByExample(ex1);
        } else {
            ex.createCriteria().andCourseEqualTo("2");
            ex1.createCriteria().andCourseEqualTo("2");
            shugouChoiceCount = choiceGeneratorMapper.countByExample(ex);
            jizuSummaryCount = summaryGeneratorMapper.countByExample(ex1);
        }

        //计算已答题数
        TotalAnswerGeneratorExample ex3 = new TotalAnswerGeneratorExample();
        TotalAnswerGeneratorExample ex4 = new TotalAnswerGeneratorExample();
        //计组选择题已答题数
        int jizuChoiceAnswered = 0;
        //计组简答题已答题数
        int jizuSummaryAnswered = 0;
        //数构选择题已答题数
        int shugouChoiceAnswered = 0;
        //数构简答题已答题数
        int shugouSummaryAnswered = 0;
        if (course.equals("1")) {
            ex3.createCriteria()
                    .andCourseEqualTo(1)
                    .andTypeEqualTo("choice")
                    .andUsernameEqualTo(userName);
            jizuChoiceAnswered = totalAnswerGeneratorMapper.countByExample(ex3);
            ex4.createCriteria()
                    .andCourseEqualTo(1)
                    .andTypeEqualTo("summary")
                    .andUsernameEqualTo(userName);
            jizuSummaryAnswered = totalAnswerGeneratorMapper.countByExample(ex4);

        } else {
            ex3.createCriteria()
                    .andCourseEqualTo(2)
                    .andTypeEqualTo("choice")
                    .andUsernameEqualTo(userName);
            shugouChoiceAnswered = totalAnswerGeneratorMapper.countByExample(ex3);
            ex4.createCriteria()
                    .andCourseEqualTo(2)
                    .andTypeEqualTo("summary")
                    .andUsernameEqualTo(userName);
            shugouSummaryAnswered = totalAnswerGeneratorMapper.countByExample(ex4);
        }
        //计组选择题正确率
        int jizuChoiceRange;
        //计组简答题正确率
        int jizuSummaryRange;
        //数构选择题正确率
        int shugouChioceRange;
        //数构简答题正确率
        int shugouSummaryRange;
        jizuChoiceRange = DataConversion.division(jizuChoiceAnswered, jizuChoiceCount);
        jizuSummaryRange = DataConversion.division(jizuSummaryAnswered, jizuSummaryCount);
        shugouChioceRange = DataConversion.division(shugouChoiceAnswered, shugouChoiceCount);
        shugouSummaryRange = DataConversion.division(shugouSummaryAnswered, shugouSummaryCount);

        TotalCountVo vo = new TotalCountVo();
        //计组
        vo.setJizuChoiceCount(jizuChoiceCount);
        vo.setJizuSummaryCount(jizuSummaryCount);
        vo.setJizuChoiceAnswered(jizuChoiceAnswered);
        vo.setJizuSummaryAnswered(jizuSummaryAnswered);
        //数构
        vo.setShugouChoiceAnswered(shugouChoiceAnswered);
        vo.setShugouChoiceCount(shugouChoiceCount);
        vo.setShugouSummaryAnswered(shugouSummaryAnswered);
        vo.setShugouSummaryCount(shugouSummaryCount);
        //正确率
        vo.setJizuChoiceRange(jizuChoiceRange);
        vo.setJizuSummaryRange(jizuSummaryRange);
        vo.setShugouChoiceRange(shugouChioceRange);
        vo.setShugouSummaryRange(shugouSummaryRange);
        return vo;
    }


}
