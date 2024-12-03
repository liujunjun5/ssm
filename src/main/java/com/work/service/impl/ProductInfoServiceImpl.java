package com.work.service.impl;

import com.work.entity.po.ProductInfo;
import com.work.entity.query.ProductInfoQuery;
import com.work.entity.query.SimplePage;
import com.work.entity.vo.PaginationResultVO;
import com.work.enums.PageSize;
import com.work.mappers.ProductInfoMappers;
import com.work.service.ProductInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:商品信息Service
 * @date:2024-12-03
 * @author: liujun
 */
@Service("ProductInfoService")
public class ProductInfoServiceImpl implements ProductInfoService {

	@Resource
	private ProductInfoMappers<ProductInfo, ProductInfoQuery> productInfoMappers;

	/**
	 * 根据条件查询列表
	 */
	public List<ProductInfo> findListByParam(ProductInfoQuery query) {
		return this.productInfoMappers.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(ProductInfoQuery query) {
		return this.productInfoMappers.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<ProductInfo> findByPage(ProductInfoQuery query) {
		Integer count = this.findCountByParam(query);
		Integer pageSize = query.getPageSize()==null?PageSize.SIZE15.getSize():query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<ProductInfo> list = this.findListByParam(query);
		PaginationResultVO<ProductInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(ProductInfo bean) {
		return this.productInfoMappers.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<ProductInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.productInfoMappers.insertBatch(listBean);
	}

	/**
	 * 批量新增或更新
	 */
	public Integer addOrUpdateBatch(List<ProductInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.productInfoMappers.insertOrUpdateBatch(listBean);
	}


	/**
	 * 根据ProductId查询
	 */
	public ProductInfo getByProductId(String productId) {
		return this.productInfoMappers.selectByProductId(productId);
	}

	/**
	 * 根据ProductId更新
	 */
	public Integer updateByProductId(ProductInfo bean, String productId) {
		return this.productInfoMappers.updateByProductId(bean, productId);
	}

	/**
	 * 根据ProductId删除
	 */
	public Integer deleteByProductId(String productId) {
		return this.productInfoMappers.deleteByProductId(productId);
	}

}