package cn.knowei.sbg.service.impl;

import cn.knowei.sbg.constants.SystemConstants;
import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.vo.*;
import cn.knowei.sbg.entity.Article;
import cn.knowei.sbg.entity.Category;
import cn.knowei.sbg.mapper.ArticleMapper;
import cn.knowei.sbg.service.ArticleService;
import cn.knowei.sbg.service.CategoryService;
import cn.knowei.sbg.utils.BeanCopyUtils;
import cn.knowei.sbg.utils.RedisCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 13:21 2023/2/23
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> qw = new LambdaQueryWrapper<>();
        //已发布文章
        qw.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        qw.orderByDesc(Article::getViewCount);
        Page<Article> page = new Page(1,10);
        page(page, qw);
        List<Article> articles = page.getRecords();
        // System.out.println(articles);
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        // List<HotArticleVo> hotArticleVos = articles.stream().map(item -> {
        //     HotArticleVo hotArticleVo = new HotArticleVo();
        //     BeanUtils.copyProperties(item, hotArticleVo);
        //     return hotArticleVo;
        // }).collect(Collectors.toList());
        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, new LambdaQueryWrapper<Article>()
                .eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId)
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                .orderByDesc(Article::getIsTop));

        List<Article> articles = page.getRecords();
        for (Article article : articles) {
            article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
        }

        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = getById(id);
        //redis获取浏览
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", String.valueOf(id));
        article.setViewCount(Long.valueOf(viewCount));
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null){
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue("article:viewCount", id.toString(), 1);
        return ResponseResult.okResult();
    }
}
