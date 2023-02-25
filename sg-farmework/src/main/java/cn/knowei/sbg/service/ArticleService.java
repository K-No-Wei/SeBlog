package cn.knowei.sbg.service;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.ArticleDto;
import cn.knowei.sbg.domain.vo.ArticleVo;
import cn.knowei.sbg.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 13:20 2023/2/23
 */
public interface ArticleService  extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult publish(ArticleVo articleVo);

    ResponseResult list(Long pageNum, Long pageSize, ArticleDto articleDto);

    ResponseResult getOne(Long id);

    ResponseResult update(ArticleVo articleVo);

    ResponseResult delete(Long id);
}
