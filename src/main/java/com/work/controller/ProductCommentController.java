package com.work.controller;

import com.work.annotation.RecordUserMessage;
import com.work.entity.constants.Constants;
import com.work.entity.po.ClaimsOfUserInfo;
import com.work.entity.po.ProductComment;
import com.work.entity.po.ProductInfo;
import com.work.entity.query.ProductCommentQuery;
import com.work.entity.vo.PaginationResultVO;
import com.work.entity.vo.ResponseVO;
import com.work.enums.MessageTypeEnum;
import com.work.exception.BusinessException;
import com.work.service.ProductCommentService;
import com.work.service.ProductInfoService;
import com.work.service.UserInfoService;
import com.work.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
//@RecordUserMessage(messageType = MessageTypeEnum.COMMENT)
public class ProductCommentController extends ABaseController{
    @Autowired
    private ProductCommentService productCommentService;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private UserInfoService userInfoService;


    //UserMessageOperationAspect切面类中使用切入点表达式@annotation...故此处注释方法
    @RequestMapping("/postComment")
    @RecordUserMessage(messageType = MessageTypeEnum.COMMENT)
    public ResponseVO postComment(String productId, String content, String imgPath,HttpServletRequest request) throws BusinessException {

        //创建商品对象，用以接收查找到的商品
        ProductInfo productInfo = productInfoService.getByProductId(productId);
        //判断商品是否上架，上架商品才能评论
        if (productInfo.getStatus() != 1) {
            throw new BusinessException("该商品已下架");
        }

        //创建商品评论对象，用以发送评论
        ProductComment comment = new ProductComment();
        //设置商品号
        comment.setProductId(productId);
        //设置评论内容
        comment.setContent(content);
        //设置图片路径
        comment.setImgPath(imgPath);
        //设置评论日期
        comment.setPostTime(new Date());
        //设置置顶否，默认未置顶
        comment.setTopType(0);
        //获取目前最大评论数
        Integer MaxCommentId = productCommentService.findMaxCommentId();
        //设置评论号为最大值加一
        comment.setCommentId(MaxCommentId+1);
        //设置商品用户ID
        comment.setProductUserId(productInfo.getProductUser());

        //设置回复人ID
        //comment.setReplyUserId();

        try {
            //获取用户对象
            String redisUserInfoKey = CookieUtils.getCookie(request,Constants.TOKEN_KEY);
            ClaimsOfUserInfo claimsOfUserInfo = userInfoService.getByTokenOfUser(redisUserInfoKey);
            //设置用户ID
            comment.setUserId(claimsOfUserInfo.getUserId());
        } catch (Exception e) {
            throw new BusinessException("处理解析错误",e);
            // 处理解析错误
        }

        //提交评论对象
        productCommentService.add(comment);
        return getSuccessResponseVO(comment);
    }


    @RequestMapping("loadComment")
    public ResponseVO loadComment(String productId, Integer pageNo) throws BusinessException{
        //创建商品对象，用以接收查找到的商品
        ProductInfo productInfo = productInfoService.getByProductId(productId);
        //判断商品是否经过审核，审核过的商品才能查看评论
        if (productInfo.getStatus() == 2 || productInfo.getStatus() == 3) {
            throw new BusinessException("该商品未经审核或审核不通过");
        }
        //获取评论队列对象
        ProductCommentQuery commentQuery = new ProductCommentQuery();
        //设置商品号
        commentQuery.setProductId(productId);
        //设置当前页面
        commentQuery.setPageNo(pageNo);
        //设置页面大小
        commentQuery.setPageSize(Constants.LENGTH_15);
        //String orderBy = orderType == null || orderType == 0 ? "like_count desc,comment_id desc" : "comment_id desc";
        //commentQuery.setOrderBy(orderBy);

        //分页查询
        PaginationResultVO<ProductComment> commentData = productCommentService.findByPage(commentQuery);

        //如果当前页面为null或1，则需要查询置顶评论
        if (pageNo == null || pageNo == 1) {
            // 从分页数据中提取TopType状态为1的评论，即置顶评论
            // 初始化一个当前页面评论列表，用于过滤未置顶评论
            List<ProductComment> topCommentList = commentData.getList().stream()
                    // 将commentData.getList()转换为流
                    // 使用filter方法过滤流中的元素
                    // 过滤条件：属于ProductComment类且TopType为1
                    .filter(comment -> comment instanceof ProductComment && comment.getTopType() == 1)
                    // 将过滤后的流元素收集到一个新的列表中
                    .collect(Collectors.toList());

            // 过滤置顶的评论，保留其他评论
            List<ProductComment> otherCommentList = commentData.getList().stream()
                    .filter(comment -> !(comment instanceof ProductComment && comment.getTopType() == 1))
                    .collect(Collectors.toList());

            // 将置顶评论列表与未置顶评论列表组合，置顶评论列表在前
            List<ProductComment> combinedCommentList = new ArrayList<>();
            combinedCommentList.addAll(topCommentList);
            combinedCommentList.addAll(otherCommentList);

            // 更新commentData中的评论列表
            commentData.setList(combinedCommentList);
        }
        return getSuccessResponseVO(commentData);
    }


    @RequestMapping("/topComment")
    public ResponseVO topComment(Integer commentId) throws BusinessException {
        try {
            ProductComment comment = productCommentService.getByCommentId(commentId);
            comment.setTopType(1);
            productCommentService.updateByCommentId(comment, commentId);
            return getSuccessResponseVO(comment);
        }catch (Exception e){
            throw new BusinessException("置顶操作出错",e);
        }
    }


    @RequestMapping("/cancelTopComment")
    public ResponseVO cancelTopComment(Integer commentId) throws BusinessException {
        try {
            ProductComment comment = productCommentService.getByCommentId(commentId);
            comment.setTopType(0);
            productCommentService.updateByCommentId(comment, commentId);
            return getSuccessResponseVO(comment);
        }catch (Exception e){
            throw new BusinessException("取消置顶失败",e);
        }
    }


    @RequestMapping("/userDelComment")
    public ResponseVO userDelComment(Integer commentId) throws BusinessException {
        //进行删除操作，并返回影响行数于结果变量中
            Integer result = productCommentService.deleteByCommentId(commentId);
            if (result > 0) {//判断操作是否成功
                return getSuccessResponseVO(commentId);
            } else {
                throw new BusinessException("删除评论数据出错");
            }
    }
}

