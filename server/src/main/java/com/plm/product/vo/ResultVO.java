package com.plm.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * chenwenhua
 * 2018\11\11
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
