package cn.knowei.sbg.service;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-02-23 18:12:40
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

}

