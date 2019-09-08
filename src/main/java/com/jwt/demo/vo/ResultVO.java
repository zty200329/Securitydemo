package com.jwt.demo.vo;

import lombok.Data;

/**
 * @author: zty
 * @date 2019/9/7 下午8:34
 * @description:
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
