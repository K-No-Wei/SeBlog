package cn.knowei.sbg.service.impl;

import cn.knowei.sbg.entity.UserRole;
import cn.knowei.sbg.mapper.UserRoleMapper;
import cn.knowei.sbg.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2023-02-26 08:46:36
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

