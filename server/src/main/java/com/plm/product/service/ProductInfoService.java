package com.plm.product.service;

import com.plm.product.common.DecreaseStockInput;
import com.plm.product.common.ProductInfoOutput;
import com.plm.product.dataobject.ProductInfo;

import java.util.List;

/**
 * chenwenhua
 * 2018\11\11
 */
public interface ProductInfoService {

    List<ProductInfo> findUpAll();

    List<ProductInfoOutput> findList(List<String> productIdList);

    //减库存
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);

}
