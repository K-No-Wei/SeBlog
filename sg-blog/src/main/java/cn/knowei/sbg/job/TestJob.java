package cn.knowei.sbg.job;

import cn.knowei.sbg.constants.SystemConstants;
import cn.knowei.sbg.entity.Article;
import cn.knowei.sbg.mapper.ArticleMapper;
import cn.knowei.sbg.service.ArticleService;
import cn.knowei.sbg.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: knowei
 * @Description: 定时任务
 * @Date: Create in 9:27 2023/2/25
 */
@Component
public class TestJob {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 每两个小时更新一次
     */
    @Scheduled(cron = "0 0 0/2 * * ? ")
    public void testJob(){
        //执行的代码
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.REDIS_ARTICLE_VIEW);
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库
        articleService.updateBatchById(articles);
    }
}
