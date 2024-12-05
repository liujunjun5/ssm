package com.work.controller;

import com.work.entity.constants.Constants;
import com.work.entity.po.ProductInfo;
import com.work.entity.po.RateInfo;
import com.work.entity.query.ProductInfoQuery;
import com.work.entity.vo.PaginationResultVO;
import com.work.entity.vo.ResponseVO;
import com.work.service.ProductInfoService;
import com.work.service.RateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
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
    //展示,增删改查


    @PostMapping("/addProduct")
    public ResponseVO addProduct(String productId, String categoryId,
                                 String productCover, String pCategoryId,
                                 String productName, String description,
                                 BigDecimal price, Integer brandId){
        if (productId.isEmpty() || pCategoryId.isEmpty() || productName.isEmpty() || price==null ) {
            return getServerErrorProductResponseVO("必要信息不能为空");
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
            productInfo.setStatus(Constants.THREE);
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
    public ResponseVO getProductByProductId(@NotEmpty String productId) {
        if (productId.isEmpty()) {
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
    public ResponseVO delProductByProductId(@NotEmpty String productId) {
        if (productId.isEmpty()) {
            return getServerErrorProductResponseVO("必要信息不能为空");
        }
        if(getProductByProductId(productId).getCode()==200){//找到对应产品后执行删除
            //ToDo 判断用户id是否相同
            productInfoService.deleteByProductId(productId);
            return getSuccessResponseVO("删除成功");
        }else {
            return getServerErrorProductResponseVO("不存在该产品id,无法删除");
        }
    }

    @PostMapping("/postProductByProductId")
    public ResponseVO updateProduct(@NotEmpty String productId, String imageUrl,
                                    String pCategoryId, @Size(min = 1, max = 10,message = "商品名大于1小于10")String productName,
                                    @Size(max = 30, message = "描述不得超过30字") String productDescription, BigDecimal price,
                                    String brandId, String stock, String tags){
        if (getProductByProductId(productId).getCode() == 200) {//找到这个产品
            //TODO 从redis拿取当前用户的信息 获得userId并验证(通过产品id找到对应用户id进行比对一致方可修改)
            if (pCategoryId.isEmpty() || productName.isEmpty() || price==null ||stock.isEmpty() || brandId.isEmpty()) {
                return getServerErrorProductResponseVO("必要信息不能为空");
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
            return getServerErrorProductResponseVO("不存在该商品");
        }
    }

    @GetMapping("/rate")
    public ResponseVO rate(@NotEmpty String productId ,Integer rate) {
        if (getProductByProductId(productId).getCode() == 200) {//找到这个产品
            if (rate>5) {
                return getServerErrorProductResponseVO("评分为0到5的整数");
            }
            RateInfo rateInfo = new RateInfo();
            rateInfo.setProductId(productId);
            rateInfo.setRate(rate);
            rateInfo.setUserId("boss");
            rateInfoService.updateRateByProductId(productId,rateInfo.getUserId(),rate);
            List<Integer> param = rateInfoService.findRateByParam(rateInfo, productId);
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
            System.out.println(relRate);
            ProductInfo productInfo = new ProductInfo();
            productInfo.setRating(relRate);
            productInfoService.updateByProductId(productInfo, productId);
            return getSuccessResponseVO("打分成功");
        } else {
            return getServerErrorProductResponseVO("不存在该商品");
        }
    }
}
