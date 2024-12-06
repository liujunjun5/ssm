package com.work.controller;

import com.work.entity.constants.Constants;
import com.work.entity.po.ProductInfo;
import com.work.entity.po.RateInfo;
import com.work.entity.query.ProductInfoQuery;
import com.work.entity.vo.PaginationResultVO;
import com.work.entity.vo.ResponseVO;
import com.work.exception.BusinessException;
import com.work.service.ProductInfoService;
import com.work.service.RateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
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

    @Autowired
    private RateInfoService rateInfoService;

    @PostMapping("/addProduct")
    public ResponseVO addProduct(String productId, String categoryId,
                                 String productCover, String pCategoryId,
                                 String productName, String description,
                                 BigDecimal price, Integer brandId) throws BusinessException {
        if (productId.isEmpty() || pCategoryId.isEmpty() || productName.isEmpty() || price==null ) {
            throw new BusinessException("必要信息不能为空");
        }
        if(getProductByProductId(productId).getCode()!=200){//找不到这个产品才可以添加
            ProductInfo productInfo = new ProductInfo();
            //TODO 从redis拿取当前用户的信息 获得userId写入表中
            productInfo.setProductId(productId);
            productInfo.setProductUser("boss");
            productInfo.setPCategoryId(Integer.valueOf(pCategoryId));
            productInfo.setCategoryId(Integer.valueOf(categoryId));
            productInfo.setImageUrl(productCover);
            productInfo.setProductName(productName);
            productInfo.setBrandId(Integer.valueOf(brandId));
            productInfo.setProductDescription(description);
            productInfo.setPrice(price);
            productInfo.setStock(5);
            productInfo.setStatus(Constants.THREE);//新增商品状态统一设置为未审核
            productInfo.setCreateTime(new Date());
            productInfo.setLastUpdateTime(new Date());
            productInfoService.add(productInfo);//执行添加
            return getSuccessResponseVO("添加成功");
        }else {
            throw new BusinessException("已存在该商品ID，无法增加");
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
    public ResponseVO getProductByProductId(@NotEmpty String productId) throws BusinessException {
        if (productId.isEmpty()) {
            throw new BusinessException("商品ID不能为空");
        }
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setProductId(productId);
        productInfoQuery.setStatus(Constants.ONE);
        List<ProductInfo> listProduct = productInfoService.findListByParam(productInfoQuery);//将查询结果放入listProduct
        System.out.println(listProduct);//打印查询结果
        if(!listProduct.isEmpty()){
            return getSuccessResponseVO(listProduct);
        }else {
            throw new BusinessException("不存在该商品，查询失败");
        }
    }

    @GetMapping("/delProductByProductId")
    public ResponseVO delProductByProductId(@NotEmpty String productId) throws BusinessException {
        if (productId.isEmpty()) {
            throw new BusinessException("必要信息不能为空");
        }
        if(getProductByProductId(productId).getCode()==200){//找到对应产品后执行删除
            //ToDo 判断用户id是否相同
            productInfoService.deleteByProductId(productId);
            return getSuccessResponseVO("删除成功");
        }else {
            throw new BusinessException("不存在该商品ID，无法删除");
        }
    }

    @PostMapping("/postProductByProductId")
    public ResponseVO updateProduct(@NotEmpty String productId, String imageUrl,
                                    String pCategoryId, @Size(min = 1, max = 10,message = "商品名大于1小于10")String productName,
                                    @Size(max = 30, message = "描述不得超过30字") String productDescription, BigDecimal price,
                                    String brandId, String stock, String tags) throws BusinessException {
        if (getProductByProductId(productId).getCode() == 200) {//找到这个产品
            //TODO 从redis拿取当前用户的信息 获得userId并验证(通过产品id找到对应用户id进行比对一致方可修改)
            if (pCategoryId.isEmpty() || productName.isEmpty() || price==null ||stock.isEmpty() || brandId.isEmpty()) {
                throw new BusinessException("必要信息不能为空");
            }
            ProductInfo productInfo = new ProductInfo();
            productInfo.setPCategoryId(Integer.valueOf(pCategoryId));
            productInfo.setImageUrl(imageUrl);
            productInfo.setProductName(productName);
            productInfo.setBrandId(Integer.valueOf(brandId));
            productInfo.setProductDescription(productDescription);
            productInfo.setPrice(price);
            productInfo.setLastUpdateTime(new Date());
            productInfo.setStock(Integer.valueOf(stock));
            productInfo.setTags(tags);
            productInfoService.updateByProductId(productInfo, productId);
            return getProductByProductId(productId);
        } else {
            throw new BusinessException("不存在该商品");
        }
    }

    @PostMapping("/rate")
    public ResponseVO rate(String productId ,Integer rate) throws BusinessException {
        if (getProductByProductId(productId).getCode() == 200) {//找到这个产品
            if (rate>5) {
                throw new BusinessException("评分为0到5的整数");
            }
            RateInfo rateInfo = new RateInfo();
            rateInfo.setProductId(productId);
            rateInfo.setRate(rate);
            rateInfo.setUserId("牛俊");
            // ToDo 读取用户信息
            if(rateInfoService.findRate(productId,rateInfo.getUserId())==null) {
               rateInfoService.addRateByProductId(rateInfo);//根据用户ID和商品ID没找到评分记录就新增
           }else {
               rateInfoService.updateRateByProductId(productId, rateInfo.getUserId(), rate);//找到了就修改
           }
            List<Integer> param = rateInfoService.findRateByParam(productId);
            int sum = 0;
            for (int i : param) {
                sum += i;
            }
            Integer relRate;
            if(sum==0){
                relRate=0;
            }else {
                relRate = sum / param.size();
            }
            System.out.println(param);
            System.out.println(relRate);
            ProductInfo productInfo = new ProductInfo();
            productInfo.setRating(relRate);
            productInfoService.updateByProductId(productInfo, productId);
            return getSuccessResponseVO("打分成功");
        } else {
            throw new BusinessException("不存在该商品");
        }
    }

    @GetMapping("/loadProductsByCid")
    public PaginationResultVO CidLoad(Integer pageNo,@NotEmpty Integer pCategoryId) throws BusinessException {
        if(pCategoryId!=null) {
            ProductInfoQuery productInfoQuery = new ProductInfoQuery();
            productInfoQuery.setPageNo(pageNo);
            productInfoQuery.setPCategoryId(pCategoryId);
            productInfoQuery.setStatus(Constants.ONE);
            return productInfoService.findByPage(productInfoQuery);
        }
        throw new BusinessException("分类ID不能为空");
    }

    @GetMapping("/purseProduct")
    public ResponseVO buy(@NotEmpty String productId){

        //Todo 引入沙箱模拟支付
        return null;
    }
}
