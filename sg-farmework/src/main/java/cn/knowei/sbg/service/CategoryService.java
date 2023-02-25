package cn.knowei.sbg.service;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.CategoryDto;
import cn.knowei.sbg.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-02-23 16:46:02
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult list(Long pageNum, Long pageSize, CategoryDto categoryDto);

    ResponseResult add(CategoryDto categoryDto);

    ResponseResult getCate(Long id);

    ResponseResult update(CategoryDto categoryDto);

    ResponseResult delete(Long id);
}

