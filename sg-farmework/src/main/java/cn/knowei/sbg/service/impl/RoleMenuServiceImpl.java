package cn.knowei.sbg.service.impl;

import cn.knowei.sbg.entity.RoleMenu;
import cn.knowei.sbg.mapper.RoleMenuMapper;
import cn.knowei.sbg.service.RoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-02-25 20:50:14
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}

