package com.work.controller;

import com.work.entity.constants.Constants;
import com.work.entity.po.ProductInfo;
import com.work.entity.query.ProductInfoQuery;
import com.work.entity.vo.PaginationResultVO;
import com.work.entity.vo.ResponseVO;
import com.work.exception.BusinessException;
import com.work.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    public ResponseVO addProduct( String productId, String productCover, String pCategoryId, String productName, @Max(100) String description, BigDecimal price, String brandId) throws BusinessException {
        if (productId==null || productId.isEmpty() ||pCategoryId==null || pCategoryId.isEmpty() || productName==null || productName.isEmpty() || price==null ) {
            return getServerErrorProductResponseVO("必要信息不能为空");
        }
        if(getProductByProductId(productId).getCode()!=200){//找不到这个产品才可以添加
            //TODO 从redis拿取当前用户的信息 获得userId
            ProductInfo productInfo = new ProductInfo();
            productInfo.setProductId(productId);
            productInfo.setPCategoryId(Integer.valueOf(pCategoryId));
            productInfo.setImageUrl(productCover);
            productInfo.setProductName(productName);
            productInfo.setBrandId(Integer.valueOf(brandId));
            productInfo.setProductDescription(description);
            productInfo.setPrice(price);
            productInfo.setStatus(Constants.TWO);
            productInfo.setCreateTime(new Date());
            productInfo.setLastUpdateTime(new Date());
            productInfoService.add(productInfo);//执行添加
            return getSuccessResponseVO("添加成功");
        }else {
            return getServerErrorIdResponseVO("已存在该产品id，无法增加");
        }
    }

    @GetMapping("/loadProducts")
    public PaginationResultVO loadProducts(Integer pageNo){
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setPageNo(pageNo);
        productInfoQuery.setStatus(Constants.ONE);
        return productInfoService.findByPage(productInfoQuery);
    }

    @GetMapping("/getProductByProductId")
    public ResponseVO getProductByProductId(String productId) {
        if (productId==null || productId.isEmpty()) {
            return getServerErrorProductResponseVO("产品id不能为空");
        }
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setProductId(productId);
        List<ProductInfo> listProduct = productInfoService.findListByParam(productInfoQuery);//将查询结果放入listProduct
        System.out.println(listProduct);//打印查询结果
        if(!listProduct.isEmpty()){
            return getSuccessResponseVO(listProduct);
        }else {
            return getServerErrorProductResponseVO("不存在该商品id,查询失败");
        }
    }

    @GetMapping("/delProductByProductId")
    public ResponseVO delProductByProductId( String productId) throws BusinessException {
        if (productId==null || productId.isEmpty()) {
            return getServerErrorProductResponseVO("必要信息不能为空");
        }
        if(getProductByProductId(productId).getCode()==200){//找到对应产品后执行删除
            //TODO判断用户id是否相同
            productInfoService.deleteByProductId(productId);
            return getSuccessResponseVO("删除成功");
        }else {
            return getServerErrorProductResponseVO("不存在该产品id,无法删除");
        }
    }
}
