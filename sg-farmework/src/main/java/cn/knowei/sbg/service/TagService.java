package cn.knowei.sbg.service;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.TagListDto;
import cn.knowei.sbg.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-02-25 10:39:58
 */
public interface TagService extends IService<Tag> {

    ResponseResult list(Integer pageNum, Integer pageNum1, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagListDto);

    ResponseResult deleteTag(Long id);

    ResponseResult updateTag(TagListDto tagService);

    ResponseResult listAllTag();
}

