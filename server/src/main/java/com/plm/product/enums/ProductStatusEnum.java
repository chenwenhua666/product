package com.plm.product.enums;

import lombok.Getter;

/**
 * chenwenhua
 * 2018\11\11
 */
@Getter
public enum ProductStatusEnum{
    UP(0,"上架中"),
    DOWN(1,"已下架")
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
