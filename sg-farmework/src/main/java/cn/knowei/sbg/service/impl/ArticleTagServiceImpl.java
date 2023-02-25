package cn.knowei.sbg.service.impl;

import cn.knowei.sbg.entity.ArticleTag;
import cn.knowei.sbg.mapper.ArticleTagMapper;
import cn.knowei.sbg.service.ArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-02-25 16:15:13
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

