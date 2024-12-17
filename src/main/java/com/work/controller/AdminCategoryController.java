package com.work.controller;

import com.work.entity.constants.Constants;
import com.work.entity.po.CategoryInfo;
import com.work.entity.query.CategoryInfoQuery;
import com.work.entity.vo.PaginationResultVO;
import com.work.entity.vo.ResponseVO;
import com.work.exception.BusinessException;
import com.work.service.CategoryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminCategoryController extends ABaseController {
    @Autowired
    private CategoryInfoService categoryInfoService;

    @RequestMapping("/category/loadCategory")
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
        return getSuccessResponseVO(result) ;
    }

    @RequestMapping("/category/saveCategory")
    public ResponseVO saveCategory(String pCategoryId,String  categoryCode,String categoryName,String categoryId,String icon,String background)throws BusinessException {
        //定义结果变量并初始化
        Integer result;
        //创建CategoryInfo对象，用于接收前端传来的参数
        CategoryInfo categoryInfo = new CategoryInfo();
        //try-catch捕获异常
        try {
            //获取最大sort字段
            Integer MaxSort = categoryInfoService.findMaxSort();
            //将参数传入CategoryInfo对象中，用以后续保存操作
            categoryInfo.setPCategoryId(Integer.valueOf(pCategoryId));
            categoryInfo.setCategoryId(Integer.valueOf(categoryId));
            categoryInfo.setCategoryCode(categoryCode);
            categoryInfo.setCategoryName(categoryName);
            categoryInfo.setIcon(icon);
            categoryInfo.setBackground(background);
            //设置sort字段为最大值MaxSort加1
            categoryInfo.setSort(MaxSort+1);
            //进行保存操作并返回影响行数于结果变量中
            result = categoryInfoService.add(categoryInfo);

        }catch (NumberFormatException e) {//数据转换错误
            throw new BusinessException("传入的数据无效", e);
        }catch(DuplicateKeyException e){//违反主键约束
            throw new BusinessException("分类ID已存在", e);
        }catch (Exception e){//其他错误
            throw new BusinessException("添加分类数据过程出错",e);
        }
        if (result > 0) {//判断是否保存成功，基于此次判断返回结果
            return getSuccessResponseVO(categoryInfo.toString());
        } else {
            throw new BusinessException("保存失败");
//            return getServerErrorResponseVO(categoryInfo.toString());
        }
    }


    // 删除分类（根据分类ID）
    @RequestMapping("/category/delCategory")
    public ResponseVO deleteCategory(String categoryId) throws BusinessException{
        //try-catch捕获异常
        try{
            //进行删除操作，并返回影响行数于结果变量中
            Integer result = categoryInfoService.deleteByCategoryId(Integer.valueOf(categoryId));
            if (result > 0) {//判断操作是否成功
                return getSuccessResponseVO(categoryId);
            } else {
                throw new BusinessException("删除失败");
//                return getServerErrorResponseVO(categoryId);
            }
        }catch (NumberFormatException e){//数据转换错误
            throw new BusinessException("传入的ID无效",e);
        }catch (Exception e){//其他错误
            throw new BusinessException("删除分类数据过程出错",e);
        }
    }

    @RequestMapping("/category/updateCategory")
    public ResponseVO updateCategory(CategoryInfo categoryInfo) throws BusinessException {
        Integer result = categoryInfoService.updateByCategoryId(categoryInfo, categoryInfo.getCategoryId());
        if (result > 0) {
            return getSuccessResponseVO(categoryInfo);
        } else {
            throw new BusinessException("更新失败");
//            return getServerErrorResponseVO(categoryInfo);
        }
    }
}
