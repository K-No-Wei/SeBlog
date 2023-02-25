package cn.knowei.sbg.service;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.entity.User;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 18:21 2023/2/23
 */
public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
