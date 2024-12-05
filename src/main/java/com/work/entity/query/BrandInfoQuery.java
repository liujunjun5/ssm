package com.work.entity.query;

import java.util.Date;

/**
 * @Description:品牌信息查询对象
 * @date:2024-12-05
 * @author: liujun
 */
public class BrandInfoQuery extends BaseQuery{
	/**
	 * 自增品牌id
	 */
	private Integer brandId;

	/**
	 * 品牌名
	 */
	private String brandName;

	private String brandNameFuzzy;

	/**
	 * 品牌描述
	 */
	private String brandDesc;

	private String brandDescFuzzy;

	/**
	 * 创建时间
	 */
	private Date createdAt;

	private String createdAtStart;

	private String createdAtEnd;

	/**
	 * 修改时间
	 */
	private Date updatedAt;

	private String updatedAtStart;

	private String updatedAtEnd;

	public void setBrandNameFuzzy(String brandNameFuzzy) {
		this.brandNameFuzzy = brandNameFuzzy;
	}

	public String getBrandNameFuzzy() {
		return this.brandNameFuzzy;
	}

	public void setBrandDescFuzzy(String brandDescFuzzy) {
		this.brandDescFuzzy = brandDescFuzzy;
	}

	public String getBrandDescFuzzy() {
		return this.brandDescFuzzy;
	}

	public void setCreatedAtStart(String createdAtStart) {
		this.createdAtStart = createdAtStart;
	}

	public String getCreatedAtStart() {
		return this.createdAtStart;
	}

	public void setCreatedAtEnd(String createdAtEnd) {
		this.createdAtEnd = createdAtEnd;
	}

	public String getCreatedAtEnd() {
		return this.createdAtEnd;
	}

	public void setUpdatedAtStart(String updatedAtStart) {
		this.updatedAtStart = updatedAtStart;
	}

	public String getUpdatedAtStart() {
		return this.updatedAtStart;
	}

	public void setUpdatedAtEnd(String updatedAtEnd) {
		this.updatedAtEnd = updatedAtEnd;
	}

	public String getUpdatedAtEnd() {
		return this.updatedAtEnd;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getBrandId() {
		return this.brandId;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandDesc(String brandDesc) {
		this.brandDesc = brandDesc;
	}

	public String getBrandDesc() {
		return this.brandDesc;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

}