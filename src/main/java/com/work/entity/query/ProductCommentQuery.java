package com.work.entity.query;

import java.util.Date;

/**
 * @Description:评论查询对象
 * @date:2024-12-05
 * @author: liujun
 */
public class ProductCommentQuery extends BaseQuery{
	/**
	 * 评论ID
	 */
	private Integer commentId;

	/**
	 * 商品ID
	 */
	private String productId;

	private String productIdFuzzy;

	/**
	 * 商品用户ID
	 */
	private String productUserId;

	private String productUserIdFuzzy;

	/**
	 * 回复内容
	 */
	private String content;

	private String contentFuzzy;

	/**
	 * 图片
	 */
	private String imgPath;

	private String imgPathFuzzy;

	/**
	 * 用户ID
	 */
	private String userId;

	private String userIdFuzzy;

	/**
	 * 回复人ID
	 */
	private String replyUserId;

	private String replyUserIdFuzzy;

	/**
	 * 0:未置顶  1:置顶
	 */
	private Integer topType;

	/**
	 * 发布时间
	 */
	private Date postTime;

	private String postTimeStart;

	private String postTimeEnd;

	public void setProductIdFuzzy(String productIdFuzzy) {
		this.productIdFuzzy = productIdFuzzy;
	}

	public String getProductIdFuzzy() {
		return this.productIdFuzzy;
	}

	public void setProductUserIdFuzzy(String productUserIdFuzzy) {
		this.productUserIdFuzzy = productUserIdFuzzy;
	}

	public String getProductUserIdFuzzy() {
		return this.productUserIdFuzzy;
	}

	public void setContentFuzzy(String contentFuzzy) {
		this.contentFuzzy = contentFuzzy;
	}

	public String getContentFuzzy() {
		return this.contentFuzzy;
	}

	public void setImgPathFuzzy(String imgPathFuzzy) {
		this.imgPathFuzzy = imgPathFuzzy;
	}

	public String getImgPathFuzzy() {
		return this.imgPathFuzzy;
	}

	public void setUserIdFuzzy(String userIdFuzzy) {
		this.userIdFuzzy = userIdFuzzy;
	}

	public String getUserIdFuzzy() {
		return this.userIdFuzzy;
	}

	public void setReplyUserIdFuzzy(String replyUserIdFuzzy) {
		this.replyUserIdFuzzy = replyUserIdFuzzy;
	}

	public String getReplyUserIdFuzzy() {
		return this.replyUserIdFuzzy;
	}

	public void setPostTimeStart(String postTimeStart) {
		this.postTimeStart = postTimeStart;
	}

	public String getPostTimeStart() {
		return this.postTimeStart;
	}

	public void setPostTimeEnd(String postTimeEnd) {
		this.postTimeEnd = postTimeEnd;
	}

	public String getPostTimeEnd() {
		return this.postTimeEnd;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getCommentId() {
		return this.commentId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductUserId(String productUserId) {
		this.productUserId = productUserId;
	}

	public String getProductUserId() {
		return this.productUserId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getImgPath() {
		return this.imgPath;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}

	public String getReplyUserId() {
		return this.replyUserId;
	}

	public void setTopType(Integer topType) {
		this.topType = topType;
	}

	public Integer getTopType() {
		return this.topType;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public Date getPostTime() {
		return this.postTime;
	}

}