package com.work.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description:评论
 * @date:2024-12-05
 * @author: liujun
 */
public class ProductComment implements Serializable {
	/**
	 * 评论ID
	 */
	private Integer commentId;

	/**
	 * 商品ID
	 */
	private String productId;

	/**
	 * 商品用户ID
	 */
	private String productUserId;

	/**
	 * 回复内容
	 */
	private String content;

	/**
	 * 图片
	 */
	private String imgPath;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 回复人ID
	 */
	private String replyUserId;

	/**
	 * 0:未置顶  1:置顶
	 */
	private Integer topType;

	/**
	 * 发布时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date postTime;


	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setProductUserId(String productUserId) {
		this.productUserId = productUserId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}

	public void setTopType(Integer topType) {
		this.topType = topType;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public Integer getCommentId() {
		return this.commentId;
	}

	public String getProductId() {
		return this.productId;
	}

	public String getProductUserId() {
		return this.productUserId;
	}

	public String getContent() {
		return this.content;
	}

	public String getImgPath() {
		return this.imgPath;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getReplyUserId() {
		return this.replyUserId;
	}

	public Integer getTopType() {
		return this.topType;
	}

	public Date getPostTime() {
		return this.postTime;
	}

	@Override
	public String toString() {
		return "评论ID:" + (commentId == null ? "null" : commentId) + ",商品ID:" + (productId == null ? "null" : productId) + ",商品用户ID:" + (productUserId == null ? "null" : productUserId) + ",回复内容:" + (content == null ? "null" : content) + ",图片:" + (imgPath == null ? "null" : imgPath) + ",用户ID:" + (userId == null ? "null" : userId) + ",回复人ID:" + (replyUserId == null ? "null" : replyUserId) + ",0:未置顶  1:置顶:" + (topType == null ? "null" : topType) + ",发布时间:" + (postTime == null ? "null" : postTime);
	}
}