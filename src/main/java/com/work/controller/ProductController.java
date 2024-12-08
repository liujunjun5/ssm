package com.work.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.work.config.AlipayConfig;
import com.work.entity.constants.Constants;
import com.work.entity.po.*;
import com.work.entity.query.BrandInfoQuery;
import com.work.entity.query.ProductInfoQuery;
import com.work.entity.vo.PaginationResultVO;
import com.work.entity.vo.ResponseVO;
import com.work.exception.BusinessException;
import com.work.service.*;
import com.work.utils.UuidTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.IOException;
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

    @Autowired
    private OrderService orderService;

    @Autowired
    private BrandInfoService brandInfoService;

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
    public ResponseVO loadProducts(Integer pageNo){
        pageNo = pageNo == null ? 1 : pageNo;
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setPageNo(pageNo);
        productInfoQuery.setStatus(Constants.ONE);
        return getSuccessResponseVO(productInfoService.findByPage(productInfoQuery));
    }


    @GetMapping("/loadRecommendProduct")//分页查询推荐且上架的商品模块
    public ResponseVO loadRecommendProduct(Integer pageNo){
        pageNo = pageNo == null ? 1 : pageNo;
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setPageNo(pageNo);
        productInfoQuery.setStatus(Constants.ONE);
        productInfoQuery.setTags("true");
        return getSuccessResponseVO(productInfoService.findByPage(productInfoQuery));
    }

    public List<BrandInfo> findById(Integer brandId) throws BusinessException{
        if (brandId==null) {
            throw new BusinessException("没有对应品牌");
        }
        BrandInfoQuery brandInfoQuery = new BrandInfoQuery();
        brandInfoQuery.setBrandId(brandId);
        List<BrandInfo> listBrand = brandInfoService.findListByParam(brandInfoQuery);//将查询结果放入listBrand
        if(!listBrand.isEmpty()){
            return listBrand;
        }else {
            throw new BusinessException("没有对应品牌");
        }
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
        if(!listProduct.isEmpty()){//找到商品后
            List<BrandInfo> brand = findById(listProduct.get(0).getBrandId());//找品牌信息
            return getSuccessResponseVO("商品信息{" +
                            listProduct  +
                            "}" + "品牌信息{" +
                            brand +
                            '}');
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
    public ResponseVO CidLoad(Integer pageNo, Integer pCategoryId) throws BusinessException {
        pageNo = pageNo == null ? 1 : pageNo;
        if(pCategoryId!=null) {
            ProductInfoQuery productInfoQuery = new ProductInfoQuery();
            productInfoQuery.setPageNo(pageNo);
            productInfoQuery.setPCategoryId(pCategoryId);
            productInfoQuery.setStatus(Constants.ONE);
            return getSuccessResponseVO(productInfoService.findByPage(productInfoQuery));
        }
        throw new BusinessException("分类ID不能为空");
    }

    @GetMapping("/purseProduct")//购买模块
    @Transactional // 添加事务管理注解
    public void buy(HttpServletResponse response, String productId) throws BusinessException, IOException {
        if (getProductById(productId)){
            ProductInfoQuery productInfoQuery = new ProductInfoQuery();
            productInfoQuery.setProductId(productId);
            List<ProductInfo> list = productInfoService.findListByParam(productInfoQuery);//将查询结果放入list
            String productName = list.get(0).getProductName();
            String description = list.get(0).getProductDescription();
            BigDecimal price = list.get(0).getPrice();//获取价格
            Integer stock = list.get(0).getStock();//获取库存数
            Integer sale = list.get(0).getSalesCount();//获取销量
            if (stock>0) {
                UuidTool s = new UuidTool();//获取生成唯一编码对象
                Order order = new Order();
                String orderNo = s.generateUniqueOrderId();//生成唯一订单编号

                //Todo 引入沙箱模拟支付
                pay(response, price, description, productName, orderNo);



                ProductInfo productInfo = new ProductInfo();
                productInfo.setProductId(productId);
                productInfo.setStock(stock-Constants.ONE);//库存减一
                productInfo.setSalesCount(sale+Constants.ONE);//销量加一
                productInfoService.updateByProductId(productInfo,productId);//更新商品表

                order.setOrderNo(orderNo);//设置唯一订单编号
                order.setPrice(price);
                order.setPayTime(new Date());
                order.setPayer("fknwof26");//购买者
                order.setPayee("wefnwofh456654");//收款方
                orderService.add(order);//写入订单表
            }
            else {
                throw new BusinessException("库存不足购买失败");
            }
        }
        throw new BusinessException("不存在该商品");
    }

    public void pay(HttpServletResponse response, BigDecimal price,
                   String orderNo, String description, String productName) throws IOException {
        //获得初始化的AlipayClient,负责调用支付宝接口
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
                AlipayConfig.app_private_key, "json", AlipayConfig.charset,
                AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no",orderNo);//订单号
        bizContent.put("total_amount",price);//交易金额
        bizContent.put("subject","购买" + productName);//商品名
        bizContent.put("body",description);//商品描述
        bizContent.put("product_code","FAST_INSTANT_TRADE_PAY");//固定配置
        alipayRequest.setBizContent(bizContent.toString());
        //支付成功跳转
        alipayRequest.setReturnUrl("http://localhost:7071/work/index.html");
        //请求
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();
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


    @PostMapping("/notify")
    public void payNotify(HttpServletRequest request){
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")){
            System.out.println("=========支付宝异步回调—--——=");

//        Map<String, String> params = new HashMap<>();
//        Map<String, String[]> requestParams = request.getParameterMap();
//        for (String name : requestParams.keySet()){
//                params.put(name, request.getParameter(name));
//        }
//        String sign = params.get("sign");
//        String content = AlipaySignature.getsignCheckContentV1(params);
//        boolean checkSignature = AlipaySignature.rsa256Cherkcontent(content, sign, AlipayConfig.alipay_public_key, "UTF-8", AlipayConfig.sign_type);
//        // 支付金验签
//        if(checkSignature){
//        // 验签通过，从沙箱传值回来
//        System.out.println("交易名称："+ params.get("subject"));
//        System.out.println("交易状态："+ params.get("trade_status"));
//        System.out.println("支付宝交易凭证号：" + params.get("trade_no"));
//        System.out.println("商户订单号；"+ params.get("out_trade_no"));
//        System.out.println("交易金额："+ params.get("total_amount"));
//        System.out.println("买家在支付宝唯-id:" + params.get("buyer_id"));
//        System.out.println("买家付款时间："+ params.get("gnt_payment"));
//        System.out.println("买家付款金额："+ params.get("buyer_pay_amount"));
//    }
        }
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



