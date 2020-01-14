package com.hs.course.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 状态码
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Status {
    private String code;
    private String msg;
}
