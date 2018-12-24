package com.plm.product.exception;

import com.plm.product.enums.ResultEnum;

/**
 * chenwenhua
 * 2018\12\15 0015
 * 16:05
 */
public class ProductException extends RuntimeException {
    private Integer code;

    public ProductException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ProductException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

}
