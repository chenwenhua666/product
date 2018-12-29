package com.plm.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.plm.product.common.DecreaseStockInput;
import com.plm.product.common.ProductInfoOutput;
import com.plm.product.dataobject.ProductInfo;
import com.plm.product.enums.ProductStatusEnum;
import com.plm.product.enums.ResultEnum;
import com.plm.product.exception.ProductException;
import com.plm.product.repository.ProductInfoRepository;
import com.plm.product.service.ProductInfoService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * chenwenhua
 * 2018\11\11 0011
 * 11:09
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Resource
    private ProductInfoRepository productInfoRepository;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfoOutput> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList).stream()
                .map(e -> {
                    ProductInfoOutput output = new ProductInfoOutput();
                    BeanUtils.copyProperties(e, output);
                    return output;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList = decreaseStockProcess(decreaseStockInputList);
        List<ProductInfoOutput> productInfoOutputList = productInfoList.stream().map(e -> {
            ProductInfoOutput productInfoOutput = new ProductInfoOutput();
            BeanUtils.copyProperties(e,productInfoOutput);
            return productInfoOutput;
        }).collect(Collectors.toList());
        //发送mq消息
        amqpTemplate.convertAndSend("productInfo-exchange","productInfo.decreaseStock",
                JSON.toJSONString(productInfoOutputList));
    }

    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList = new ArrayList<>();
        for (DecreaseStockInput stockInput : decreaseStockInputList) {
            ProductInfo productInfo = productInfoRepository.findById(stockInput.getProductId()).orElse(null);
            if (productInfo == null) {
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - stockInput.getProductQuantity();
            if(result < 0){
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }

}
