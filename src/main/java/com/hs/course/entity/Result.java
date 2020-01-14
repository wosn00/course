package com.hs.course.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * json返回包装数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result<T> {
    /**
     * 响应状态码
     */
    private int code;

    /**
     * 提示信息
     */
    private String message;
    /**
     * 具体响应数据
     */
    private T data;

}
