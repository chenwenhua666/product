package com.plm.product.service.impl;

import com.plm.product.dataobject.ProductCategory;
import com.plm.product.repository.ProductCategoryRepository;
import com.plm.product.service.ProductCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * chenwenhua
 * 2018\11\11
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Resource
    private ProductCategoryRepository repository;

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

}
