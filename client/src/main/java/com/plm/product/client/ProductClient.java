package com.plm.product.client;

import com.plm.product.common.DecreaseStockInput;
import com.plm.product.common.ProductInfoOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * chenwenhua
 * 2018\11\18 0018
 * 13:44
 */
@FeignClient(name = "PRODUCT",fallback = ProductClient.FeignClientFallBack.class)
public interface ProductClient {
    @GetMapping("/msg")
    String productMsg();

    @PostMapping("/product/ListForOrder")
    List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList);

    @PostMapping("/product/decreaseStock")
    void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList);

    @Component
    static class FeignClientFallBack implements ProductClient{
        @Override
        public String productMsg() {
            return null;
        }

        @Override
        public List<ProductInfoOutput> listForOrder(List<String> productIdList) {
            return null;
        }

        @Override
        public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {

        }
    }
}
