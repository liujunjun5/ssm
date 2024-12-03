package com.work.controller;

import com.work.entity.po.ProductInfo;
import com.work.entity.query.ProductInfoQuery;
import com.work.entity.vo.ResponseVO;
import com.work.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Validated
@RestController//直接返回JSON数据类型
@RequestMapping("/product")
public class ProductController extends ABaseController {
    @Autowired
    private ProductInfoService productInfoService;

    //展示,增删改查
    @PostMapping("/addProduct")
    public ResponseVO addProduct(String productId,String productCover, String pCategoryId, String productName, String description, BigDecimal price,String brandId) {
        if(getProductByProductId(productId).getCode()!=200){//找不到这个产品才可以添加
            ProductInfo productInfo = new ProductInfo();
            productInfo.setProductId(productId);
            productInfo.setPCategoryId(Integer.valueOf(pCategoryId));
            productInfo.setImageUrl(productCover);
            productInfo.setProductName(productName);
            productInfo.setBrandId(Integer.valueOf(brandId));
            productInfo.setProductDescription(description);
            productInfo.setPrice(price);
            productInfo.setCreateTime(new Date());
            productInfo.setLastUpdateTime(new Date());
            productInfoService.add(productInfo);//执行添加
            return getSuccessResponseVO("添加成功");
        }else {
            return getServerErrorIdResponseVO("已存在该产品id，无法增加");
        }
    }

    @GetMapping("/loadProducts")
    public ResponseVO loadProducts(Integer pageNo){
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setPageNo(pageNo);
        productInfoQuery.getPageNo();
        List<ProductInfo> list = productInfoService.findListByParam(productInfoQuery);
        System.out.println(list);
        return getSuccessResponseVO(null);
    }

    @GetMapping("/getProductByProductId")
    public ResponseVO getProductByProductId(String productId){
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setProductId(productId);
        List<ProductInfo> listProduct = productInfoService.findListByParam(productInfoQuery);//将查询结果放入listProduct
        System.out.println(listProduct);//打印查询结果
        if(!listProduct.isEmpty()){
            return getSuccessResponseVO("查询成功");
        }else {
            return getServerErrorProductResponseVO("不存在该商品id,查询失败");
        }
    }

    @GetMapping("/delProductByProductId")
    public ResponseVO delProductByProductId(String productId){
        if(getProductByProductId(productId).getCode()==200){//找到对应产品后执行删除
            productInfoService.deleteByProductId(productId);
            return getSuccessResponseVO("删除成功");
        }else {
            return getServerErrorProductResponseVO("不存在该产品id,无法删除");
        }
    }
}
