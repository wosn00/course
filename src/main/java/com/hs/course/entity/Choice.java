package com.hs.course.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Choice {
    int id;
    int course;
    int chapter;
    String title;
    String a;
    String b;
    String c;
    String d;
    String answer;
    String explain;
    String img;
    String exa;
    /**
     * 此题在该章节下的题号
     */
    int countno;

    public Choice(int id, String title, int countno) {
        this.id = id;
        this.title = title;
        this.countno = countno;
    }
}
