package com.work.entity.po;

import java.io.Serializable;


public class RateInfo implements Serializable {
	private int rateId;

	/**
	 * 商品ID
	 */
	private String productId;

	private String UserId;
	/**
	 * 商品评分
	 */
	private Integer rate;


	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "rateInfo{" +
				"productId='" + productId + '\'' +
				", UserId='" + UserId + '\'' +
				", rate=" + rate +
				'}';
	}
}