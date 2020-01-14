package com.hs.course.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Summary {
    int id;
    int course;
    int chapter;
    String title;
    String answer;
    String keyword;
    String img;
    /**
     * 此题在该章节下的题号
     */
    int countno;

    public Summary(int id, String title, int countno) {
        this.id = id;
        this.title = title;
        this.countno = countno;
    }
}
