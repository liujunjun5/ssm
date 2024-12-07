package com.work.service.impl;

import com.work.service.ProductCommentService;
import com.work.entity.po.ProductComment;
import com.work.entity.query.SimplePage;
import com.work.entity.query.ProductCommentQuery;
import com.work.entity.vo.PaginationResultVO;
import com.work.enums.PageSize;
import com.work.mappers.ProductCommentMappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:评论Service
 * @date:2024-12-05
 * @author: liujun
 */
@Service("ProductCommentService")
public class ProductCommentServiceImpl implements ProductCommentService {

	@Resource
	private ProductCommentMappers<ProductComment, ProductCommentQuery> productCommentMappers;

	/**
	 * 根据条件查询列表
	 */
	public List<ProductComment> findListByParam(ProductCommentQuery query) {
		return this.productCommentMappers.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(ProductCommentQuery query) {
		return this.productCommentMappers.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<ProductComment> findByPage(ProductCommentQuery query) {
		Integer count = this.findCountByParam(query);
		Integer pageSize = query.getPageSize()==null?PageSize.SIZE15.getSize():query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<ProductComment> list = this.findListByParam(query);
		PaginationResultVO<ProductComment> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(ProductComment bean) {
		return this.productCommentMappers.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<ProductComment> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.productCommentMappers.insertBatch(listBean);
	}

	/**
	 * 批量新增或更新
	 */
	public Integer addOrUpdateBatch(List<ProductComment> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.productCommentMappers.insertOrUpdateBatch(listBean);
	}


	/**
	 * 根据CommentId查询
	 */
	public ProductComment getByCommentId(Integer commentId) {
		return this.productCommentMappers.selectByCommentId(commentId);
	}

	/**
	 * 根据CommentId更新
	 */
	public Integer updateByCommentId(ProductComment bean, Integer commentId) {
		return this.productCommentMappers.updateByCommentId(bean, commentId);
	}

	/**
	 * 根据CommentId删除
	 */
	public Integer deleteByCommentId(Integer commentId) {
		return this.productCommentMappers.deleteByCommentId(commentId);
	}

	/**
	 * 查找最大CommentId
	 */
	public Integer findMaxCommentId(){
		Integer MaxCommentId = this.productCommentMappers.findMaxCommentId();
		return MaxCommentId==null?0:MaxCommentId;
	};

}