package com.hs.course.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Questions {
    private Integer id;

    private Integer userid;

    private String course;

    private String type;

    private Integer problemid;

    private Date time;

    Questions(Integer userid, String type, String course, Integer problemid) {
        this.userid = userid;
        this.type = type;
        this.course = course;
        this.problemid = problemid;
    }

}
