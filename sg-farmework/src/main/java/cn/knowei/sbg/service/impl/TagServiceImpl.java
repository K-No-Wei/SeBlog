package cn.knowei.sbg.service.impl;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.TagListDto;
import cn.knowei.sbg.domain.vo.PageVo;
import cn.knowei.sbg.domain.vo.TagVo;
import cn.knowei.sbg.entity.Tag;
import cn.knowei.sbg.mapper.TagMapper;
import cn.knowei.sbg.service.TagService;
import cn.knowei.sbg.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-02-25 10:39:58
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult list(Integer pageNum, Integer pageNum1, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> qw = new LambdaQueryWrapper<>();
        qw.eq(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        qw.eq(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());
        Page<Tag> page = new Page<>();
        page(page, qw);

        List<Tag> tagList = page.getRecords();

        PageVo pageVo = new PageVo(tagList, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        this.getBaseMapper().deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateTag(TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);

        updateById(tag);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        List<Tag> tags = list();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tags, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }
}

