package com.work.entity.query;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:商品信息查询对象
 * @date:2024-12-03
 * @author: liujun
 */
public class ProductInfoQuery extends BaseQuery{
	/**
	 * 商品ID
	 */
	private String productId;

	private String productIdFuzzy;

	/**
	 * 商品名称
	 */
	private String productName;

	private String productNameFuzzy;

	/**
	 * 商品描述
	 */
	private String productDescription;

	private String productDescriptionFuzzy;

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
	private Date createTime;

	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime;

	private String lastUpdateTimeStart;

	private String lastUpdateTimeEnd;

	/**
	 * 商品状态 0:下架 1:上架 2:审核不通过 3：未审核
	 */
	private Integer status;

	/**
	 * 商品图片
	 */
	private String imageUrl;

	private String imageUrlFuzzy;

	/**
	 * 商品标签
	 */
	private String tags;

	private String tagsFuzzy;

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

	public void setProductIdFuzzy(String productIdFuzzy) {
		this.productIdFuzzy = productIdFuzzy;
	}

	public String getProductIdFuzzy() {
		return this.productIdFuzzy;
	}

	public void setProductNameFuzzy(String productNameFuzzy) {
		this.productNameFuzzy = productNameFuzzy;
	}

	public String getProductNameFuzzy() {
		return this.productNameFuzzy;
	}

	public void setProductDescriptionFuzzy(String productDescriptionFuzzy) {
		this.productDescriptionFuzzy = productDescriptionFuzzy;
	}

	public String getProductDescriptionFuzzy() {
		return this.productDescriptionFuzzy;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeStart() {
		return this.createTimeStart;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getCreateTimeEnd() {
		return this.createTimeEnd;
	}

	public void setLastUpdateTimeStart(String lastUpdateTimeStart) {
		this.lastUpdateTimeStart = lastUpdateTimeStart;
	}

	public String getLastUpdateTimeStart() {
		return this.lastUpdateTimeStart;
	}

	public void setLastUpdateTimeEnd(String lastUpdateTimeEnd) {
		this.lastUpdateTimeEnd = lastUpdateTimeEnd;
	}

	public String getLastUpdateTimeEnd() {
		return this.lastUpdateTimeEnd;
	}

	public void setImageUrlFuzzy(String imageUrlFuzzy) {
		this.imageUrlFuzzy = imageUrlFuzzy;
	}

	public String getImageUrlFuzzy() {
		return this.imageUrlFuzzy;
	}

	public void setTagsFuzzy(String tagsFuzzy) {
		this.tagsFuzzy = tagsFuzzy;
	}

	public String getTagsFuzzy() {
		return this.tagsFuzzy;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductDescription() {
		return this.productDescription;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getBrandId() {
		return this.brandId;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getStock() {
		return this.stock;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTags() {
		return this.tags;
	}

	public void setSalesCount(Integer salesCount) {
		this.salesCount = salesCount;
	}

	public Integer getSalesCount() {
		return this.salesCount;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getRating() {
		return this.rating;
	}

	public void setPCategoryId(Integer pCategoryId) {
		this.pCategoryId = pCategoryId;
	}

	public Integer getPCategoryId() {
		return this.pCategoryId;
	}

}