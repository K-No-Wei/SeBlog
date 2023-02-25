package cn.knowei.sbg.service.impl;

import cn.knowei.sbg.constants.SystemConstants;
import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.CategoryDto;
import cn.knowei.sbg.domain.vo.CategoryVo;
import cn.knowei.sbg.domain.vo.PageVo;
import cn.knowei.sbg.entity.Article;
import cn.knowei.sbg.entity.Category;
import cn.knowei.sbg.mapper.CategoryMapper;
import cn.knowei.sbg.service.ArticleService;
import cn.knowei.sbg.service.CategoryService;
import cn.knowei.sbg.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-02-23 16:46:02
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        LambdaQueryWrapper<Article> qw = new LambdaQueryWrapper<Article>()
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        List<Article> articleList = articleService.list(qw);
        List<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toList());

        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(category -> SystemConstants.ARTICLE_STATUS_NORMAL == Integer.parseInt(category.getStatus()))
                .collect(Collectors.toList());

        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        return null;
    }

    /**
     * 分类
     * @param pageNum
     * @param pageSize
     * @param categoryDto
     * @return
     */
    @Override
    public ResponseResult list(Long pageNum, Long pageSize, CategoryDto categoryDto) {
        LambdaQueryWrapper<Category> qw = new LambdaQueryWrapper<>();

        Page<Category> page = new Page<>(pageNum, pageSize);
        page(page, qw);

        List<CategoryDto> categoryDtos = BeanCopyUtils.copyBeanList(page.getRecords(), CategoryDto.class);

        return ResponseResult.okResult(new PageVo(categoryDtos, page.getTotal()));
    }

    @Override
    @Transactional
    public ResponseResult add(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        save(category);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCate(Long id) {
        Category category = getById(id);
        CategoryDto categoryDto = BeanCopyUtils.copyBean(category, CategoryDto.class);
        return ResponseResult.okResult(categoryDto);
    }

    @Override
    public ResponseResult update(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        updateById(category);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(Long id) {
        this.getBaseMapper().deleteById(id);
        return ResponseResult.okResult();
    }
}

