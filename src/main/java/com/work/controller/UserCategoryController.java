package com.work.controller;

import com.work.entity.constants.Constants;
import com.work.entity.po.CategoryInfo;
import com.work.entity.query.CategoryInfoQuery;
import com.work.entity.vo.PaginationResultVO;
import com.work.entity.vo.ResponseVO;
import com.work.service.CategoryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class UserCategoryController extends ABaseController{

        @Autowired
        private CategoryInfoService categoryInfoService;

        @RequestMapping("/loadCategory")
        public ResponseVO loadCategory(Integer pageNo) {
            //判断页面值是否输入
            pageNo = pageNo == null ? 1 : pageNo;
            // 创建查询对象，并设置分页参数
            CategoryInfoQuery query = new CategoryInfoQuery();
            //设置当前页面值与页面大小
            query.setPageNo(pageNo);
            query.setPageSize(Constants.LENGTH_15);
            // 调用服务层方法获取分页结果
            PaginationResultVO<CategoryInfo> result = categoryInfoService.findByPage(query);

            // 返回分页结果
            return getSuccessResponseVO(result);
        }

}
