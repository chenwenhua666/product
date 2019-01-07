package com.plm.product.controller;

import com.plm.product.common.DecreaseStockInput;
import com.plm.product.common.ProductInfoOutput;
import com.plm.product.dataobject.ProductCategory;
import com.plm.product.dataobject.ProductInfo;
import com.plm.product.service.ProductCategoryService;
import com.plm.product.service.ProductInfoService;
import com.plm.product.utils.ResultVOUtil;
import com.plm.product.vo.ProductInfoVO;
import com.plm.product.vo.ProductVO;
import com.plm.product.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductInfoService productInfoService;

    @Resource
    private ProductCategoryService productCategoryService;

    /**
     * 1. 查询所有在架的商品
     * 2. 获取类目type列表
     * 3. 查询类目
     * 4. 构造数据
     */
    @GetMapping("/list")
    public ResultVO<ProductVO> list() {
        //查询所有已上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType).collect(Collectors.toList());
        //查询商品类目
        List<ProductCategory> productCategoryList = productCategoryService
                .findByCategoryTypeIn(categoryTypeList);

        //拼装数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory: productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo:productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }

            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);

        }
        return ResultVOUtil.success(productVOList);
    }

    @PostMapping("/ListForOrder")
    public List<ProductInfoOutput> ListForOrder(@RequestBody List<String> productIdList){
        try {
            Thread.sleep(3000);
        } catch (Exception e){
            e.printStackTrace();
        }
        return productInfoService.findList(productIdList);
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList) {
        productInfoService.decreaseStock(decreaseStockInputList);
    }


}
