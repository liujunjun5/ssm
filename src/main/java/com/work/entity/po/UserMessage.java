package com.work.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description:用户消息表
 * @date:2024-12-08
 * @author: liujun
 */
public class UserMessage implements Serializable {
	/**
	 * 消息ID自增
	 */
	private Integer messageId;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 主体ID
	 */
	private String productId;

	/**
	 * 消息类型
	 */
	private Integer messageType;

	/**
	 * 发送人ID
	 */
	private String sendUserId;

	/**
	 * 0:未读 1:已读
	 */
	private Integer readType;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	private String productName;

	private String sendUserName;

	private String productCover;

	private String sendUserAvatar;
	private String messageContent;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public String getProductCover() {
		return productCover;
	}

	public void setProductCover(String productCover) {
		this.productCover = productCover;
	}

	public String getSendUserAvatar() {
		return sendUserAvatar;
	}

	public void setSendUserAvatar(String sendUserAvatar) {
		this.sendUserAvatar = sendUserAvatar;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public void setReadType(Integer readType) {
		this.readType = readType;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getMessageId() {
		return this.messageId;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getProductId() {
		return this.productId;
	}

	public Integer getMessageType() {
		return this.messageType;
	}

	public String getSendUserId() {
		return this.sendUserId;
	}

	public Integer getReadType() {
		return this.readType;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}


	@Override
	public String toString() {
		return "消息ID自增:" + (messageId == null ? "null" : messageId) + ",用户ID:" + (userId == null ? "null" : userId) + ",主体ID:" + (productId == null ? "null" : productId) + ",消息类型:" + (messageType == null ? "null" : messageType) + ",发送人ID:" + (sendUserId == null ? "null" : sendUserId) + ",0:未读 1:已读:" + (readType == null ? "null" : readType) + ",创建时间:" + (createTime == null ? "null" : createTime);
	}


}