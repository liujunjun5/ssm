package com.work.entity.query;


/**
 * @Description:分类信息查询对象
 * @date:2024-12-03
 * @author: liujun
 */
public class CategoryInfoQuery extends BaseQuery{
	/**
	 * 自增分类ID
	 */
	private Integer categoryId;

	/**
	 * 分类编码
	 */
	private String categoryCode;

	private String categoryCodeFuzzy;

	/**
	 * 分类名称
	 */
	private String categoryName;

	private String categoryNameFuzzy;

	/**
	 * 父级分类ID
	 */
	private Integer pCategoryId;

	/**
	 * 图标
	 */
	private String icon;

	private String iconFuzzy;

	/**
	 * 背景图
	 */
	private String background;

	private String backgroundFuzzy;

	/**
	 * 排序号
	 */
	private Integer sort;

	public void setCategoryCodeFuzzy(String categoryCodeFuzzy) {
		this.categoryCodeFuzzy = categoryCodeFuzzy;
	}

	public String getCategoryCodeFuzzy() {
		return this.categoryCodeFuzzy;
	}

	public void setCategoryNameFuzzy(String categoryNameFuzzy) {
		this.categoryNameFuzzy = categoryNameFuzzy;
	}

	public String getCategoryNameFuzzy() {
		return this.categoryNameFuzzy;
	}

	public void setIconFuzzy(String iconFuzzy) {
		this.iconFuzzy = iconFuzzy;
	}

	public String getIconFuzzy() {
		return this.iconFuzzy;
	}

	public void setBackgroundFuzzy(String backgroundFuzzy) {
		this.backgroundFuzzy = backgroundFuzzy;
	}

	public String getBackgroundFuzzy() {
		return this.backgroundFuzzy;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryCode() {
		return this.categoryCode;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setPCategoryId(Integer pCategoryId) {
		this.pCategoryId = pCategoryId;
	}

	public Integer getPCategoryId() {
		return this.pCategoryId;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getBackground() {
		return this.background;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSort() {
		return this.sort;
	}

}