package com.work.controller;

import com.work.entity.constants.Constants;
import com.work.entity.po.*;
import com.work.entity.query.BrandInfoQuery;
import com.work.entity.query.ProductInfoQuery;
import com.work.entity.vo.PaginationResultVO;
import com.work.entity.vo.ResponseVO;
import com.work.exception.BusinessException;
import com.work.service.*;
import com.work.utils.CookieUtils;
import com.work.utils.UuidTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Autowired
    private PayService payService;

    private String userId;
    //   String userId = "牛俊";

    //获取用户信息
    public Boolean getUserId(HttpServletRequest request) throws BusinessException {
        if (userInfoService != null) {
            ClaimsOfUserInfo claimsOfUserInfo = userInfoService.getByTokenOfUser(CookieUtils.getCookie(request,Constants.TOKEN_KEY));
            if (claimsOfUserInfo.getUserId().isEmpty()){
                throw new BusinessException("没有用户信息");
            }else {
                this.userId = claimsOfUserInfo.getUserId();
                return true;
            }
        }
        throw new BusinessException("获取用户服务不可用");
    }


    //通过商品ID找上架的商品
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

    //根据商品ID找对应商家
    public String getProductUser(String productId) throws BusinessException {
        if (productId.isEmpty()) {
            throw new BusinessException("商品ID不能为空");
        }
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setProductId(productId);
        List<ProductInfo> listProduct = productInfoService.findListByParam(productInfoQuery);//将查询结果放入listProduct
        return listProduct.get(0).getProductUser();//获取对应商家
    }

    @PostMapping("/addProduct")//添加未审核商品模块
    public ResponseVO addProduct(Integer categoryId, Integer stock,
                                 String productCover, Integer pCategoryId,
                                 @Size(min = 1, max = 10,message = "商品名大于1小于10")String productName,
                                 @Size(max = 30, message = "描述不得超过30字")String description,HttpServletRequest request,
                                 BigDecimal price, Integer brandId) throws BusinessException {
        if (pCategoryId==null || productName.isEmpty() || price==null || stock==null) {
            throw new BusinessException("必要信息不能为空");
        }
        if(getUserId(request)){//获取到用户信息才可以添加
            ProductInfo productInfo = new ProductInfo();
            UuidTool s = new UuidTool();//获取生成唯一编码对象
            String productId = s.generateUniqueProductId();
            if (!getProductById(productId)) {//避免生成相同ID引起主键冲突
                productInfo.setProductId(productId);
                productInfo.setProductUser(userId);
                productInfo.setPCategoryId(pCategoryId);
                productInfo.setCategoryId(categoryId);
                productInfo.setImageUrl(productCover);
                productInfo.setProductName(productName);
                productInfo.setStock(stock);
                productInfo.setBrandId(brandId);
                productInfo.setTags("false");//默认不推荐
                productInfo.setProductDescription(description);
                productInfo.setPrice(price);
                productInfo.setStatus(Constants.THREE);//新增商品状态统一设置为未审核
                productInfo.setCreateTime(new Date());
                productInfo.setLastUpdateTime(new Date());
                productInfoService.add(productInfo);//执行添加
                return getSuccessResponseVO("添加成功");
            }
            else throw new BusinessException("商品ID生成重复，请重试");
        }else {
            throw new BusinessException("没有用户信息，请登录");
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

    //返回品牌信息
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
    public ResponseVO delProductByProductId(String productId, HttpServletRequest request) throws BusinessException {
        if(getProductById(productId) && getUserId(request)){//找到对应产品后执行删除
            //判断用户id是否相同
            if(getProductUser(productId)==userId){
            productInfoService.deleteByProductId(productId);
            return getSuccessResponseVO("删除成功");
            }
            else throw new BusinessException("非该商品拥有者，无法删除");
        }else {
            throw new BusinessException("不存在该商品ID，无法删除");
        }
    }
    @PostMapping("/postProductByProductId")//修改商品信息模块
    public ResponseVO updateProduct(String productId, String imageUrl,Integer categoryId,HttpServletRequest request,
                                    Integer pCategoryId, @Size(min = 1, max = 10,message = "商品名大于1小于10")String productName,
                                    @Size(max = 30, message = "描述不得超过30字") String productDescription, BigDecimal price,
                                    Integer brandId, Integer stock, String tags) throws BusinessException {
        if (getProductById(productId)) {//找到这个产品
            if (pCategoryId==null || productName.isEmpty() || price==null ||stock==null || brandId==null) {
                throw new BusinessException("必要信息不能为空");
            }
            //从redis拿取当前用户的信息 获得userId并验证(通过产品id找到对应用户id进行比对一致方可修改)
            if (getUserId(request) && getProductUser(productId)==userId){
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
            }
            else throw new BusinessException("非该商品拥有者，无法修改");
        } else {
            throw new BusinessException("不存在该商品");
        }
    }

    @PostMapping("/rate")//打分模块
    public ResponseVO rate(HttpServletRequest request,String productId ,Integer rate) throws BusinessException {
        if (rate>5 || rate<0) {throw new BusinessException("评分为0到5的整数");}
        if (getProductById(productId) && getUserId(request)) {//找到这个产品,获取用户信息,
            if (orderService.findRecord(userId,productId)) {// 存在购买记录
                RateInfo rateInfo = new RateInfo();
                rateInfo.setProductId(productId);
                rateInfo.setRate(rate);
                rateInfo.setUserId(userId);
                if (rateInfoService.findRate(productId, rateInfo.getUserId()) == null) {
                    rateInfoService.addRateByProductId(rateInfo);//根据用户ID和商品ID没找到评分记录就新增
                } else {
                    rateInfoService.updateRateByProductId(productId, rateInfo.getUserId(), rate);//找到了就修改
                }
                updateProductInfo(productId);
                return getSuccessResponseVO(rateInfo);
            }else throw new BusinessException("未购买，无法评分");
        } else throw new BusinessException("不存在该商品");
    }

    @GetMapping("/findRate")//查询历史评分
    public ResponseVO findRate(HttpServletRequest request) throws BusinessException {
        if (getUserId(request)){
        List<RateInfo> rateList = rateInfoService.findRateList(userId);
        if (!rateList.isEmpty()) {
            return getSuccessResponseVO(rateList);
        }
        }
        throw new BusinessException("该用户没有历史评分");
    }

    @GetMapping("/delRate")//删除历史评分
    public ResponseVO delRate(HttpServletRequest request, String productId) throws BusinessException {
        if(getUserId(request) && getProductById(productId) && rateInfoService.findRate(productId,userId)!=null) {
            rateInfoService.deleteRate(productId,userId);
            updateProductInfo(productId);
            return getSuccessResponseVO("删除成功");
        }
        throw new BusinessException("没有对应记录，无法删除");
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


    @PostMapping("/purseProduct") // 购买模块
    @Transactional // 添加事务管理注解
    public String buy(HttpServletRequest request, String[] productIds, String consignee, String address, String phone, Integer[] amounts) throws BusinessException, UnsupportedEncodingException {
        boolean isPhoneNumberValid = phone.matches("^\\d{11}$"); // 判断手机号格式
        if (getUserId(request) && isPhoneNumberValid) { // 获取用户信息
            UuidTool s = new UuidTool(); // 创建唯一编码对象
            BigDecimal Amount = BigDecimal.ZERO; // 初始化总金额
            StringBuilder productNameBuilder = new StringBuilder(); // 用于构建商品名称列表
            String Name = null;
            List<Order> orders = new ArrayList<>(); // 存储所有订单
            if (productIds != null && amounts !=null) {
                for (int i = 0; i < productIds.length; i++) {
                    String productId = productIds[i];
                    Integer amount = amounts[i];
                    if (!getProductById(productId) && amount<1) { // 找到商品才能购买
                        throw new BusinessException("不存在该商品无法购买: " + productId + "或购买数量只能为大于0的整数");
                    }
                    ProductInfoQuery productInfoQuery = new ProductInfoQuery();
                    productInfoQuery.setProductId(productId);
                    List<ProductInfo> list = productInfoService.findListByParam(productInfoQuery); // 将查询结果放入list
                    Integer stock = list.get(0).getStock(); // 获取库存数
                    String payee = list.get(0).getProductUser();//获取商户
                    BigDecimal price = list.get(0).getPrice();//获取价格
                    String productName = list.get(0).getProductName();//获取商品名
                    Integer sale = list.get(0).getSalesCount();//获取销量
                    if (stock < amount) { // 库存大于购买数量才能购买
                        throw new BusinessException("库存不足购买失败: " + productId);
                    }
                    Order order = new Order();
                    String orderNo = s.generateUniqueOrderId();//生成唯一订单编号
                    ProductInfo productInfo = new ProductInfo();
                    productInfo.setProductId(productId);
                    productInfo.setStock(stock - amount);//库存减少
                    productInfo.setSalesCount(sale + amount);//销量增加
                    productInfoService.updateByProductId(productInfo, productId);//更新商品表
                    BigDecimal bigDecimalFromAmount = new BigDecimal(amount.toString());// 将 Integer 转换为 BigDecimal
                    BigDecimal totalAmount = price.multiply(bigDecimalFromAmount);
                    order.setOrderNo(orderNo);//设置唯一订单编号
                    order.setPrice(totalAmount);//交易金额
                    order.setProductId(productId);//交易产品
                    order.setAmount(amount);//购买数量
                    order.setAddress(address);//收货地址
                    order.setConsignee(consignee);//收货人
                    order.setPhone(phone);//购买人电话
                    order.setPayTime(new Date());//交易时间
                    order.setPayer(userId);//购买者
                    order.setPayee(payee);//收款方
                    orders.add(order);
                    if (productNameBuilder.length() > 0) {productNameBuilder.append(", ");}// 添加分隔符
                    productNameBuilder.append(productName); // 添加商品名称
                    Amount = Amount.add(totalAmount); // 累加每个订单的金额
                    Name = productNameBuilder.toString();
                }
            } else throw new BusinessException("传入数组为空");
            orderService.add(orders);//循环写入订单表
            String No = s.generateUniqueOrderId();//给沙箱生成订单编号
            return creatPay(No, String.valueOf(Amount), Name);//这是给支付宝沙箱那边的信息
        }
        throw new BusinessException("用户验证失败请重新登录,或请检查手机号码格式");
    }

    @PostMapping("pay")
    public String creatPay(String id, String price, String name) throws UnsupportedEncodingException {
        return payService.pay(id,price,name);
    }

    @PostMapping("/notify")
    public void payNotify(String trade_no, String total_amount, String trade_status){
        System.out.println("支付宝订单编号：" + trade_no);
        System.out.println("支付宝订单编号：" + total_amount);
        System.out.println("支付宝订单编号：" + trade_status);
    }

    @GetMapping("/findOrder")//查询购买订单模块
    public ResponseVO findOrder(HttpServletRequest request) throws BusinessException {
        if (getUserId(request)) {
            List<Order> listOrder = orderService.findOrder(userId);//找到订单表
            if (!listOrder.isEmpty()) {
                return getSuccessResponseVO(listOrder);
            } else throw new BusinessException("没有订单");
        }
        else throw new BusinessException("用户认证失败，请重新登陆");
    }

    @GetMapping("/bossFindOrder")//查询销售订单模块
    public ResponseVO bossFindOrder(HttpServletRequest request) throws BusinessException {
        if (getUserId(request)) {
            List<Order> listOrder = orderService.bossFindOrder(userId);//找到订单表
            if (!listOrder.isEmpty()) {
                return getSuccessResponseVO(listOrder);
            } else throw new BusinessException("没有订单");
        }
        else throw new BusinessException("用户认证失败，请重新登陆");
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



