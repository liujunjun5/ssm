package com.work.controller;


import com.work.entity.po.ProductInfo;
import com.work.entity.query.ProductInfoQuery;
import com.work.entity.vo.ResponseVO;
import com.work.exception.BusinessException;
import com.work.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/admin")
public class AdminController extends ABaseController {
    @Autowired
    private ProductInfoService productInfoService;
    //根据商品ID找
    public Boolean getProductByProductId(String productId) {
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setProductId(productId);
        List<ProductInfo> listProduct = productInfoService.findListByParam(productInfoQuery);//将查询结果放入listProduct
        System.out.println(listProduct);//打印查询结果
        return !listProduct.isEmpty();//true则为找到该商品
    }
    @GetMapping("/product/audit")
    public ResponseVO audit(String productId, Integer status) throws BusinessException {
        if (productId.isEmpty() || status==null || status<0 || status>2) {
            throw new BusinessException("必要信息不能为空或状态只能为0,1,2");
        }
        if(getProductByProductId(productId)){//找到对应产品后执行审核
            ProductInfo productInfo = new ProductInfo();
            productInfo.setStatus(status);
            productInfoService.updateByProductId(productInfo,productId);//修改上架状态
            return getSuccessResponseVO("修改成功");
        }else {
            throw new BusinessException("不存在该商品ID，无法审核");
        }
    }
}
