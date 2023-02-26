package cn.knowei.sbg.service;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.UserDto;
import cn.knowei.sbg.domain.dto.UserListDto;
import cn.knowei.sbg.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-02-23 18:23:46
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult list(Long pageNum, Long pageSize, UserDto userDto);

    ResponseResult add(UserListDto userListDto);

    ResponseResult getOne(Long id);

    ResponseResult update(UserListDto userListDto);
}

