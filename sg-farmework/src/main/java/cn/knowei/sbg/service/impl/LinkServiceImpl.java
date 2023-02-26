package cn.knowei.sbg.service.impl;

import cn.knowei.sbg.constants.SystemConstants;
import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.LinkDto;
import cn.knowei.sbg.domain.dto.LinkAllDto;
import cn.knowei.sbg.domain.vo.LinkVo;
import cn.knowei.sbg.domain.vo.PageVo;
import cn.knowei.sbg.entity.Link;
import cn.knowei.sbg.mapper.LinkMapper;
import cn.knowei.sbg.service.LinkService;
import cn.knowei.sbg.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-02-23 18:12:40
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查找审核通过的
        LambdaQueryWrapper<Link> qw = new LambdaQueryWrapper<>();
        qw.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> lists = list(qw);
        List<LinkVo> linkVoList = BeanCopyUtils.copyBeanList(lists, LinkVo.class);
        return ResponseResult.okResult(linkVoList);
    }

    @Override
    public ResponseResult list(Long pageNum, Long pageSize, LinkDto linkDto) {
        LambdaQueryWrapper<Link> qw = new LambdaQueryWrapper<>();
        qw.eq(StringUtils.hasText(linkDto.getName()), Link::getName, linkDto.getName());
        qw.eq(StringUtils.hasText(linkDto.getStatus()), Link::getStatus, linkDto.getStatus());

        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page, qw);

        List<LinkAllDto> linkDtos = BeanCopyUtils.copyBeanList(page.getRecords(), LinkAllDto.class);
        return ResponseResult.okResult(new PageVo(linkDtos, page.getTotal()));
    }

    @Override
    public ResponseResult add(LinkAllDto linkAddDto) {
        Link link = BeanCopyUtils.copyBean(linkAddDto, Link.class);
        save(link);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getOne(Long id) {
        Link link = getOne(new LambdaQueryWrapper<Link>().eq(Link::getId, id));
        LinkAllDto linkAllDto = BeanCopyUtils.copyBean(link, LinkAllDto.class);

        return ResponseResult.okResult(linkAllDto);
    }

    @Override
    public ResponseResult update(LinkAllDto linkAllDto) {
        Link link = BeanCopyUtils.copyBean(linkAllDto, Link.class);
        updateById(link);

        return ResponseResult.okResult();
    }
}

