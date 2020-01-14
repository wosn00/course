package com.hs.course.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionsVo {
    private Integer id;

    private Integer userid;

    private String course;

    private String type;

    private Integer problemid;

    private String time;
    /**
     * 题目
     */
    private String title;
    /**
     * 章节
     */
    private int chapter;


}
