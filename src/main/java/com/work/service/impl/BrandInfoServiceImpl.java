package com.work.service.impl;

import com.work.service.BrandInfoService;
import com.work.entity.po.BrandInfo;
import com.work.entity.query.SimplePage;
import com.work.entity.query.BrandInfoQuery;
import com.work.entity.vo.PaginationResultVO;
import com.work.enums.PageSize;
import com.work.mappers.BrandInfoMappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:品牌信息Service
 * @date:2024-12-05
 * @author: liujun
 */
@Service("BrandInfoService")
public class BrandInfoServiceImpl implements BrandInfoService {

	@Resource
	private BrandInfoMappers<BrandInfo, BrandInfoQuery> brandInfoMappers;

	/**
	 * 根据条件查询列表
	 */
	public List<BrandInfo> findListByParam(BrandInfoQuery query) {
		return this.brandInfoMappers.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(BrandInfoQuery query) {
		return this.brandInfoMappers.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<BrandInfo> findByPage(BrandInfoQuery query) {
		Integer count = this.findCountByParam(query);
		Integer pageSize = query.getPageSize()==null?PageSize.SIZE15.getSize():query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<BrandInfo> list = this.findListByParam(query);
		PaginationResultVO<BrandInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(BrandInfo bean) {
		return this.brandInfoMappers.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<BrandInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.brandInfoMappers.insertBatch(listBean);
	}

	/**
	 * 批量新增或更新
	 */
	public Integer addOrUpdateBatch(List<BrandInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.brandInfoMappers.insertOrUpdateBatch(listBean);
	}


	/**
	 * 根据BrandId查询
	 */
	public BrandInfo getByBrandId(Integer brandId) {
		return this.brandInfoMappers.selectByBrandId(brandId);
	}

	/**
	 * 根据BrandId更新
	 */
	public Integer updateByBrandId(BrandInfo bean, Integer brandId) {
		return this.brandInfoMappers.updateByBrandId(bean, brandId);
	}

	/**
	 * 根据BrandId删除
	 */
	public Integer deleteByBrandId(Integer brandId) {
		return this.brandInfoMappers.deleteByBrandId(brandId);
	}

}