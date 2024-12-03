package com.work.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:商品信息
 * @date:2024-12-03
 * @author: liujun
 */
public class ProductInfo implements Serializable {
	/**
	 * 商品ID
	 */
	private String productId;

	/**
	 * 商品名称
	 */
	private String productName;

	/**
	 * 商品描述
	 */
	private String productDescription;

	/**
	 * 子分类ID
	 */
	private Integer categoryId;

	/**
	 * 品牌ID
	 */
	private Integer brandId;

	/**
	 * 商品价格
	 */
	private BigDecimal price;

	/**
	 * 库存数量
	 */
	private Integer stock;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 最后更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;

	/**
	 * 商品状态 0:下架 1:上架 2:审核不通过 3：未审核
	 */
	@JsonIgnore
	private Integer status;

	/**
	 * 商品图片
	 */
	private String imageUrl;

	/**
	 * 商品标签
	 */
	private String tags;

	/**
	 * 销售数量
	 */
	private Integer salesCount;

	/**
	 * 商品评分
	 */
	private Integer rating;

	/**
	 * 分类ID
	 */
	private Integer pCategoryId;


	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setSalesCount(Integer salesCount) {
		this.salesCount = salesCount;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public void setPCategoryId(Integer pCategoryId) {
		this.pCategoryId = pCategoryId;
	}

	public String getProductId() {
		return this.productId;
	}

	public String getProductName() {
		return this.productName;
	}

	public String getProductDescription() {
		return this.productDescription;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public Integer getBrandId() {
		return this.brandId;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public Integer getStock() {
		return this.stock;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public String getTags() {
		return this.tags;
	}

	public Integer getSalesCount() {
		return this.salesCount;
	}

	public Integer getRating() {
		return this.rating;
	}

	public Integer getPCategoryId() {
		return this.pCategoryId;
	}

	@Override
	public String toString() {
		return "商品ID:" + (productId == null ? "null" : productId) + ",商品名称:" + (productName == null ? "null" : productName) + ",商品描述:" + (productDescription == null ? "null" : productDescription) + ",子分类ID:" + (categoryId == null ? "null" : categoryId) + ",品牌ID:" + (brandId == null ? "null" : brandId) + ",商品价格:" + (price == null ? "null" : price) + ",库存数量:" + (stock == null ? "null" : stock) + ",创建时间:" + (createTime == null ? "null" : createTime) + ",最后更新时间:" + (lastUpdateTime == null ? "null" : lastUpdateTime) + ",商品状态 0:下架 1:上架 2:审核不通过 3：未审核:" + (status == null ? "null" : status) + ",商品图片:" + (imageUrl == null ? "null" : imageUrl) + ",商品标签:" + (tags == null ? "null" : tags) + ",销售数量:" + (salesCount == null ? "null" : salesCount) + ",商品评分:" + (rating == null ? "null" : rating) + ",分类ID:" + (pCategoryId == null ? "null" : pCategoryId);
	}
}