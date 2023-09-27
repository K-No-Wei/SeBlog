package cn.knowei.sbg.runner;

import cn.knowei.sbg.constants.SystemConstants;
import cn.knowei.sbg.entity.Article;
import cn.knowei.sbg.mapper.ArticleMapper;
import cn.knowei.sbg.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 9:19 2023/2/25
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // 尺寸博客信息， id----viewCount
        List<Article> articles = articleMapper.selectList(null);
        //stream流
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(
                        article -> article.getId().toString(),
                        article -> {
                            return article.getViewCount().intValue();
                        }
                ));
        //存入redis中
        redisCache.setCacheMap(SystemConstants.REDIS_ARTICLE_VIEW, viewCountMap);
    }
}
