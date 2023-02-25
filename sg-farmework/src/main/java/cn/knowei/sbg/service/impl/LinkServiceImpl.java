package cn.knowei.sbg.service.impl;

import cn.knowei.sbg.constants.SystemConstants;
import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.vo.LinkVo;
import cn.knowei.sbg.entity.Link;
import cn.knowei.sbg.mapper.LinkMapper;
import cn.knowei.sbg.service.LinkService;
import cn.knowei.sbg.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}

