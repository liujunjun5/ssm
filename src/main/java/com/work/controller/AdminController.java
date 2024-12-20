package com.work.controller;


import com.work.annotation.RecordUserMessage;
import com.work.entity.constants.Constants;
import com.work.entity.po.BrandInfo;
import com.work.entity.po.ProductInfo;
import com.work.entity.query.BrandInfoQuery;
import com.work.entity.query.ProductInfoQuery;
import com.work.entity.vo.ResponseVO;
import com.work.enums.MessageTypeEnum;
import com.work.exception.BusinessException;
import com.work.service.BrandInfoService;
import com.work.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Validated
@RestController
@RequestMapping("/admin")
public class AdminController extends ABaseController {
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private BrandInfoService brandInfoService;
    //根据商品ID找
    public Boolean getProductByProductId(String productId) {
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setProductId(productId);
        List<ProductInfo> listProduct = productInfoService.findListByParam(productInfoQuery);//将查询结果放入listProduct
        return !listProduct.isEmpty();//true则为找到该商品
    }


    @GetMapping("/product/audit")//审核模块
    @RecordUserMessage(messageType = MessageTypeEnum.SYS)
    public ResponseVO audit(String productId, Integer status) throws BusinessException {
        if (productId.isEmpty() || status==null || status<0 || status>2) {
            throw new BusinessException("必要信息不能为空或状态只能为0,1,2");
        }
        if(getProductByProductId(productId)){//找到对应产品后执行审核
            ProductInfo productInfo = new ProductInfo();
            productInfo.setStatus(status);
            productInfoService.updateByProductId(productInfo,productId);//修改上架状态
            return getSuccessResponseVO("修改成功");
        }else {throw new BusinessException("不存在该商品ID，无法审核");}
    }

    @GetMapping("/product/RecommendProduct")//推荐模块（可以不上架但依然推荐）
    @RecordUserMessage(messageType = MessageTypeEnum.SYS)
    public ResponseVO recommend(String productId) throws BusinessException {
        if (productId.isEmpty()) {
            throw new BusinessException("商品ID不能为空");
        }
        if(getProductByProductId(productId)){//找到对应产品后执行推荐
            ProductInfo productInfo = new ProductInfo();
            productInfo.setTags("true");
            productInfoService.updateByProductId(productInfo,productId);//修改推荐
            return getSuccessResponseVO("推荐成功");
        }else {
            throw new BusinessException("不存在该商品ID，无法推荐");
        }
    }

    @GetMapping("/product/CancelRecommendProduct")//取消推荐模块
    public ResponseVO CRP(String productId) throws BusinessException {
        if (productId.isEmpty()) {
            throw new BusinessException("商品ID不能为空");
        }
        if(getProductByProductId(productId)){//找到对应产品后执行推荐
            ProductInfo productInfo = new ProductInfo();
            productInfo.setTags("false");
            productInfoService.updateByProductId(productInfo,productId);//取消推荐
            return getSuccessResponseVO("取消推荐成功");
        }else {
            throw new BusinessException("不存在该商品ID，无法取消推荐");
        }
    }

    @GetMapping("/givenProducts")//分页查找指定状态商品模块
    public ResponseVO loadProducts(Integer pageNo, Integer status){
        pageNo = pageNo == null ? 1 : pageNo;
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setPageNo(pageNo);
        productInfoQuery.setStatus(status);
        return getSuccessResponseVO(productInfoService.findByPage(productInfoQuery));
    }

    @PostMapping("/brand/saveBrand")//增加品牌模块
    public ResponseVO save(String brandDesc, String brandName) throws BusinessException {
        if(!brandName.isEmpty() && brandName.length()<11 && brandDesc.length()<256) {
            BrandInfo brandInfo = new BrandInfo();
            brandInfo.setBrandDesc(brandDesc);
            brandInfo.setBrandName(brandName);
            brandInfo.setCreatedAt(new Date());
            brandInfo.setUpdatedAt(new Date());
            brandInfoService.add(brandInfo);
            return getSuccessResponseVO("添加成功");
        }
        throw new BusinessException("品牌名不能为空且应小于11个字符，描述不得多余256字符");
    }
    @GetMapping("/brand/delBrand")//删除品牌模块
    public ResponseVO delBrand(Integer brandId) throws BusinessException {
        if(brandId!=null && findById(brandId).getCode()==200){
            brandInfoService.deleteByBrandId(brandId);
            return getSuccessResponseVO("删除成功");
        }
        throw new BusinessException("品牌ID不能为空");
    }
    @GetMapping("/brand/getBrand")//获取品牌列表模块
    public ResponseVO getBrand(Integer pageNo){
        pageNo = pageNo == null ? 1 : pageNo;
        BrandInfoQuery brandInfoQuery = new BrandInfoQuery();
        brandInfoQuery.setPageNo(pageNo);
        return getSuccessResponseVO(brandInfoService.findByPage(brandInfoQuery));
    }

    @PostMapping("/brand/postBrand")//修改品牌模块
    public ResponseVO update(Integer brandId, String brandDesc, String brandName) throws BusinessException {
        if(findById(brandId).getCode()==200 && !brandName.isEmpty() && brandName.length()<11 && brandDesc.length()<256) {
            BrandInfo brandInfo = new BrandInfo();
            brandInfo.setBrandId(brandId);
            brandInfo.setBrandDesc(brandDesc);
            brandInfo.setBrandName(brandName);
            brandInfo.setUpdatedAt(new Date());
            brandInfoService.updateByBrandId(brandInfo,brandId);
            return findById(brandId);
        }
        throw new BusinessException("品牌名不能为空且应小于11个字符，描述不得多余256字符");
    }

    @GetMapping("/brand/findBrand")//根据ID查找品牌模块
    public ResponseVO findById(Integer brandId) throws BusinessException{
        if (brandId==null) {
            throw new BusinessException("品牌ID不能为空");
        }
        BrandInfoQuery brandInfoQuery = new BrandInfoQuery();
        brandInfoQuery.setBrandId(brandId);
        List<BrandInfo> listBrand = brandInfoService.findListByParam(brandInfoQuery);//将查询结果放入listBrand
        if(!listBrand.isEmpty()){
            return getSuccessResponseVO(listBrand);
        }else {
            throw new BusinessException("不存在该品牌ID");
        }
    }
}
