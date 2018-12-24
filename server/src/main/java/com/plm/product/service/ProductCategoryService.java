package com.plm.product.service;


import com.plm.product.dataobject.ProductCategory;

import java.util.List;

/**
 * chenwenhua
 * 2018\11\11
 */
public interface ProductCategoryService {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

}
