package cn.knowei.sbg.service.impl;

import cn.knowei.sbg.constants.SystemConstants;
import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.ArticleDto;
import cn.knowei.sbg.domain.vo.*;
import cn.knowei.sbg.entity.Article;
import cn.knowei.sbg.entity.ArticleTag;
import cn.knowei.sbg.entity.Category;
import cn.knowei.sbg.entity.Tag;
import cn.knowei.sbg.mapper.ArticleMapper;
import cn.knowei.sbg.service.ArticleService;
import cn.knowei.sbg.service.ArticleTagService;
import cn.knowei.sbg.service.CategoryService;
import cn.knowei.sbg.utils.BeanCopyUtils;
import cn.knowei.sbg.utils.RedisCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
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

    @Autowired
    private ArticleTagService articleTagService;
    @Override
    public ResponseResult hotArticleList() {

        List<HotArticleVo> redisArticle = redisCache.getCacheObject("HotArticle:blog");
        if (!Objects.isNull(redisArticle) && redisArticle.size()!=0) {
            System.out.println("-------------------缓存路过-------------------------");
            return ResponseResult.okResult(redisArticle);
        }
        LambdaQueryWrapper<Article> qw = new LambdaQueryWrapper<>();
        //已发布文章
        qw.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        qw.orderByDesc(Article::getViewCount);
        Page<Article> page = new Page(1,10);
        page(page, qw);
        List<Article> articles = page.getRecords();
        // System.out.println(articles);
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        System.out.println("---------------------没有缓存--------------------------");
        redisCache.setCacheObject("HotArticle:blog", hotArticleVos, 30, TimeUnit.SECONDS);

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
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_ARTICLE_VIEW, String.valueOf(id));
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
        redisCache.incrementCacheMapValue(SystemConstants.REDIS_ARTICLE_VIEW, id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult publish(ArticleVo articleVo) {
        Article article = BeanCopyUtils.copyBean(articleVo, Article.class);
        save(article);

        List<ArticleTag> articleTags = articleVo.getTags().stream()
                .map(tag -> new ArticleTag(article.getId(), tag))
                .collect(Collectors.toList());

        articleTagService.saveBatch(articleTags);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult list(Long pageNum, Long pageSize, ArticleDto articleDto) {
        LambdaQueryWrapper<Article> qw = new LambdaQueryWrapper<>();
        qw.eq(StringUtils.hasText(articleDto.getTitle()), Article::getTitle, articleDto.getTitle());
        qw.eq(StringUtils.hasText(articleDto.getSummary()), Article::getSummary, articleDto.getSummary());

        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, qw);

        return ResponseResult.okResult(new PageVo(page.getRecords(), page.getTotal()));
    }

    @Override
    public ResponseResult getOne(Long id) {
        List<ArticleTag> list = articleTagService.list(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, id));
        List<Long> tags = list.stream()
                .map(ArticleTag::getTagId)
                .collect(Collectors.toList());
        Article article = getById(id);
        ArticleVo articleVo = BeanCopyUtils.copyBean(article, ArticleVo.class);
        articleVo.setTags(tags);

        return ResponseResult.okResult(articleVo);

    }

    @Override
    @Transactional
    public ResponseResult update(ArticleVo articleVo) {
        //更新文章
        Article article = BeanCopyUtils.copyBean(articleVo, Article.class);
        updateById(article);

        //文章标签全部删除
        List<Long> tags = articleVo.getTags();
        for (Long tag : tags) {
            articleTagService.remove(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getTagId, tag));
        }
        //文章标签增加
        List<ArticleTag> articleTags = articleVo.getTags().stream()
                .map(tag -> new ArticleTag(article.getId(), tag))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(Long id) {
        this.getBaseMapper().deleteById(id);
        return ResponseResult.okResult();
    }
}
