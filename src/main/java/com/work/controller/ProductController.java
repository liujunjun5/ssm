package com.work.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.work.config.AlipayConfig;
import com.work.entity.constants.Constants;
import com.work.entity.po.ClaimsOfUserInfo;
import com.work.entity.po.ProductInfo;
import com.work.entity.po.RateInfo;
import com.work.entity.query.ProductInfoQuery;
import com.work.entity.vo.PaginationResultVO;
import com.work.entity.vo.ResponseVO;
import com.work.exception.BusinessException;
import com.work.service.ProductInfoService;
import com.work.service.RateInfoService;
import com.work.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    @Autowired
    private UserInfoService userInfoService;

    //通过ID找商品
    public Boolean getProductById(String productId) throws BusinessException {
        if (productId.isEmpty()) {
            throw new BusinessException("商品ID不能为空");
        }
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setProductId(productId);
        productInfoQuery.setStatus(Constants.ONE);
        List<ProductInfo> listProduct = productInfoService.findListByParam(productInfoQuery);//将查询结果放入listProduct
        return !listProduct.isEmpty();//true则为找到该上架商品
    }

    @PostMapping("/addProduct")//添加未审核商品模块
    public ResponseVO addProduct(String productId, Integer categoryId, Integer stock,
                                 String productCover, Integer pCategoryId,
                                 @Size(min = 1, max = 10,message = "商品名大于1小于10")String productName,
                                 @Size(max = 30, message = "描述不得超过30字")String description,
                                 BigDecimal price, Integer brandId) throws BusinessException {
        if (pCategoryId==null || productName.isEmpty() || price==null || stock==null) {
            throw new BusinessException("必要信息不能为空");
        }
        if(!getProductById(productId)){//找不到这个产品才可以添加
            ProductInfo productInfo = new ProductInfo();
            //TODO 从redis拿取当前用户的信息 获得userId写入表中
//            ClaimsOfUserInfo claimsOfUserInfo = userInfoService.getByTokenFromRedis(cookie.getValue());
//            claimsOfUserInfo.getUserId();
            productInfo.setProductId(productId);
            productInfo.setProductUser("boss");
            productInfo.setPCategoryId(pCategoryId);
            productInfo.setCategoryId(categoryId);
            productInfo.setImageUrl(productCover);
            productInfo.setProductName(productName);
            productInfo.setStock(stock);
            productInfo.setBrandId(brandId);
            productInfo.setProductDescription(description);
            productInfo.setPrice(price);
            productInfo.setStatus(Constants.THREE);//新增商品状态统一设置为未审核
            productInfo.setCreateTime(new Date());
            productInfo.setLastUpdateTime(new Date());
            productInfoService.add(productInfo);//执行添加
            return getSuccessResponseVO("添加成功");
        }else {
            throw new BusinessException("已存在该商品ID，无法添加");
        }
    }

    @GetMapping("/loadProducts")//分页查找上架商品模块
    public PaginationResultVO loadProducts(Integer pageNo){
        pageNo = pageNo == null ? 1 : pageNo;
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setPageNo(pageNo);
        productInfoQuery.setStatus(Constants.ONE);
        return productInfoService.findByPage(productInfoQuery);
    }

    @GetMapping("/getProductByProductId")//根据商品ID查找上架商品模块
    public ResponseVO getProductByProductId(String productId) throws BusinessException {
        if (productId.isEmpty()) {
            throw new BusinessException("商品ID不能为空");
        }
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setProductId(productId);
        productInfoQuery.setStatus(Constants.ONE);
        List<ProductInfo> listProduct = productInfoService.findListByParam(productInfoQuery);//将查询结果放入listProduct
        if(!listProduct.isEmpty()){
            return getSuccessResponseVO(listProduct);
        }else {
            throw new BusinessException("不存在该商品");
        }
    }

    @GetMapping("/delProductByProductId")//删除商品模块
    public ResponseVO delProductByProductId(String productId) throws BusinessException {
        if(getProductById(productId)){//找到对应产品后执行删除
            //ToDo 判断用户id是否相同
            productInfoService.deleteByProductId(productId);
            return getSuccessResponseVO("删除成功");
        }else {
            throw new BusinessException("不存在该商品ID，无法删除");
        }
    }
    @PostMapping("/postProductByProductId")//修改商品信息模块
    public ResponseVO updateProduct(String productId, String imageUrl,Integer categoryId,
                                    Integer pCategoryId, @Size(min = 1, max = 10,message = "商品名大于1小于10")String productName,
                                    @Size(max = 30, message = "描述不得超过30字") String productDescription, BigDecimal price,
                                    Integer brandId, Integer stock, String tags) throws BusinessException {
        if (getProductById(productId)) {//找到这个产品
            //TODO 从redis拿取当前用户的信息 获得userId并验证(通过产品id找到对应用户id进行比对一致方可修改)
            if (pCategoryId==null || productName.isEmpty() || price==null ||stock==null || brandId==null) {
                throw new BusinessException("必要信息不能为空");
            }
            ProductInfo productInfo = new ProductInfo();
            productInfo.setPCategoryId(pCategoryId);
            productInfo.setCategoryId(categoryId);
            productInfo.setImageUrl(imageUrl);
            productInfo.setProductName(productName);
            productInfo.setBrandId(brandId);
            productInfo.setProductDescription(productDescription);
            productInfo.setPrice(price);
            productInfo.setLastUpdateTime(new Date());
            productInfo.setStock(stock);
            productInfo.setTags(tags);
            productInfoService.updateByProductId(productInfo, productId);
            return getProductByProductId(productId);
        } else {
            throw new BusinessException("不存在该商品");
        }
    }

    @PostMapping("/rate")//打分模块
    public ResponseVO rate(String productId ,Integer rate) throws BusinessException {
        if (getProductById(productId)) {//找到这个产品
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
            updateProductInfo(productId);
            return getSuccessResponseVO(rateInfo);
        } else {
            throw new BusinessException("不存在该商品");
        }
    }

    @GetMapping("/findRate")//查询历史评分
    public ResponseVO findRate() throws BusinessException {
        String userId = null;
        //ToDo 获取userId
        List<RateInfo> rateList = rateInfoService.findRateList("牛俊");
        System.out.println(rateList);
        if (!rateList.isEmpty()) {
            return getSuccessResponseVO(rateList);
        }
        throw new BusinessException("该用户没有历史评分");
    }

    @GetMapping("/delRate")//删除历史评分
    public ResponseVO delRate(String productId) throws BusinessException {
        String userId = "牛俊";
        //ToDo 获取userId
        if(getProductById(productId) && rateInfoService.findRate(productId,userId)!=null) {
            rateInfoService.deleteRate(productId,userId);
            updateProductInfo(productId);
            return getSuccessResponseVO("删除成功");
        }
        throw new BusinessException("没有对应记录，删除失败");
    }

    //根据商品ID求平均评分并写入product_info表
    private void updateProductInfo(String productId) {
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
    }

    @GetMapping("/loadProductsByCid")//根据分类查找商品模块
    public PaginationResultVO CidLoad(Integer pageNo, Integer pCategoryId) throws BusinessException {
        pageNo = pageNo == null ? 1 : pageNo;
        if(pCategoryId!=null) {
            ProductInfoQuery productInfoQuery = new ProductInfoQuery();
            productInfoQuery.setPageNo(pageNo);
            productInfoQuery.setPCategoryId(pCategoryId);
            productInfoQuery.setStatus(Constants.ONE);
            return productInfoService.findByPage(productInfoQuery);
        }
        throw new BusinessException("分类ID不能为空");
    }

    @GetMapping("/purseProduct")//购买模块
    @Transactional // 添加事务管理注解
    public ResponseVO buy(String productId) throws BusinessException {
        if (getProductById(productId)){
            ProductInfoQuery productInfoQuery = new ProductInfoQuery();
            productInfoQuery.setProductId(productId);
            List<ProductInfo> list = productInfoService.findListByParam(productInfoQuery);//将查询结果放入list
            BigDecimal price = list.get(0).getPrice();//获取价格
            Integer stock = list.get(0).getStock();//获取库存数
            Integer sale = list.get(0).getSalesCount();//获取销量
            if (stock>0) {
                //Todo 引入沙箱模拟支付

                ProductInfo productInfo = new ProductInfo();
                productInfo.setProductId(productId);
                productInfo.setStock(stock-Constants.ONE);//库存减一
                productInfo.setSalesCount(sale+Constants.ONE);//销量加一
                productInfoService.updateByProductId(productInfo,productId);//更新商品表
                return getSuccessResponseVO("购买成功");
            }
            else {
                throw new BusinessException("库存不足购买失败");
            }
        }
        throw new BusinessException("不存在该商品");
    }

    @GetMapping("/buy")//购买模块
    public void payController(HttpServletRequest request, HttpServletResponse response, BigDecimal price) throws IOException {

        //获得初始化的AlipayClient,负责调用支付宝接口
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
                AlipayConfig.app_private_key, "json", AlipayConfig.charset,
                AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        // 商户订单号
        String out_trade_no ="14989sgsgsesf";
        price= BigDecimal.valueOf(55);
        // 付款金额
        BigDecimal total_amount = price;
        // 订单名称
        String subject = "116486487fsejkbfsjbfs1";
        // 商品描述
        String body = "哈哈哈";
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":\"" + total_amount
                + "\"," + "\"subject\":\"" + subject + "\"," + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        System.out.println("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\"," + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        //请求
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest,"GET").getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        // 将支付表单嵌入HTML并输出到页面
        System.out.println(form);
        response.setContentType("text/html;charset=" + AlipayConfig.charset);
        response.getWriter().write(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }

    @RequestMapping("/payNotify")
    @ResponseBody
    public String payNotify(HttpServletResponse response){

        return "success";
    }



    @RequestMapping("/searchProduct")
    public ResponseVO searchProduct(@NotEmpty String productName, Integer pageNo) throws BusinessException {
        pageNo = pageNo == null ? 1 : pageNo;
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setProductNameFuzzy(productName);
        productInfoQuery.setPageNo(pageNo);
        productInfoQuery.setStatus(Constants.ONE);
//        List<ProductInfo> productInfos = productInfoService.findByPage(productInfoQuery);
        PaginationResultVO<ProductInfo> productInfos = productInfoService.findByPage(productInfoQuery);
        if (productInfos == null) {
            throw new BusinessException("商品不存在");
        }
        return getSuccessResponseVO(productInfos);
    }
}



