package com.hs.course.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccuracyRateVo {
    Integer jizuChoiceRate;
    Integer jizuSummaryRate;
    Integer shugouChoiceRate;
    Integer shugouSummaryRate;
}
