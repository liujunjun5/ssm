package com.work.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description:品牌信息
 * @date:2024-12-05
 * @author: liujun
 */
public class BrandInfo implements Serializable {
	/**
	 * 自增品牌id
	 */
	private Integer brandId;

	/**
	 * 品牌名
	 */
	private String brandName;

	/**
	 * 品牌描述
	 */
	private String brandDesc;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdAt;

	/**
	 * 修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatedAt;


	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public void setBrandDesc(String brandDesc) {
		this.brandDesc = brandDesc;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getBrandId() {
		return this.brandId;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public String getBrandDesc() {
		return this.brandDesc;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	@Override
	public String toString() {
		return "自增品牌id:" + (brandId == null ? "null" : brandId) + ",品牌名:" + (brandName == null ? "null" : brandName) + ",品牌描述:" + (brandDesc == null ? "null" : brandDesc) + ",创建时间:" + (createdAt == null ? "null" : createdAt) + ",修改时间:" + (updatedAt == null ? "null" : updatedAt);
	}
}