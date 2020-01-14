package com.hs.course.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamQueryReq {
    private List<ChoiceQueryReq> choice;
    private List<ChoiceQueryReq> summary;

}
