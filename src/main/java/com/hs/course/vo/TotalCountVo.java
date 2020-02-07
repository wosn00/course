package com.hs.course.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalCountVo {
    /**
     * 计组选择题总数
     */
    int jizuChoiceCount;
    /**
     * 数构选择题总数
     */
    int shugouChoiceCount;
    /**
     * 计组简答题总数
     */
    int jizuSummaryCount;
    /**
     * 数构简答题总数
     */
    int shugouSummaryCount;
    /**
     * 计组选择题已答题数
     */
    int jizuChoiceAnswered;
    /**
     * 计组简答题已答题数
     */
    int jizuSummaryAnswered;
    /**
     * 数构选择题已答题数
     */
    int shugouChoiceAnswered;
    /**
     * 数构简答题已答题数
     */
    int shugouSummaryAnswered;


    /**
     * 计组选择题已答题比例
     */
    int jizuChoiceRange;
    /**
     * 数构选择题已答题比例
     */
    int shugouChoiceRange;
    /**
     * 计组简答题已答题比例
     */
    int jizuSummaryRange;
    /**
     * 数构简答题已答题比例
     */
    int shugouSummaryRange;

}
