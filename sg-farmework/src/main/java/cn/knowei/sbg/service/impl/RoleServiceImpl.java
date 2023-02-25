package cn.knowei.sbg.service.impl;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.RoleDto;
import cn.knowei.sbg.domain.dto.RoleLessDto;
import cn.knowei.sbg.domain.dto.RoleMoreDto;
import cn.knowei.sbg.domain.vo.PageVo;
import cn.knowei.sbg.entity.Role;
import cn.knowei.sbg.entity.RoleMenu;
import cn.knowei.sbg.mapper.RoleMapper;
import cn.knowei.sbg.service.RoleMenuService;
import cn.knowei.sbg.service.RoleService;
import cn.knowei.sbg.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-02-25 11:26:24
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long userId) {
        if (userId == 1L){
            ArrayList<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        return getBaseMapper().selectRoleKeyByUserId(userId);
    }

    @Override
    public ResponseResult list(Long pageNum, Long pageSize, RoleDto roleDto) {
        LambdaQueryWrapper<Role> qw = new LambdaQueryWrapper<>();
        qw.eq(StringUtils.hasText(roleDto.getRoleName()), Role::getRoleName, roleDto.getRoleName());
        qw.eq(StringUtils.hasText(roleDto.getStatus()), Role::getStatus, roleDto.getStatus());

        Page<Role> page = new Page<>();
        page(page, qw);

        return ResponseResult.okResult(new PageVo(page.getRecords(), page.getTotal()));

    }

    @Override
    public ResponseResult changeStatus(RoleLessDto roleLessDto) {
        this.getBaseMapper().updateStatusByRoleId(roleLessDto);

        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult add(RoleMoreDto roleMoreDto) {
        Role role = BeanCopyUtils.copyBean(roleMoreDto, Role.class);
        save(role);

        List<Long> menuIds = roleMoreDto.getMenuIds();
        List<RoleMenu> roleMenus = menuIds.stream()
                .map(m -> new RoleMenu(role.getId(), m))
                .collect(Collectors.toList());

        roleMenuService.saveBatch(roleMenus);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getOne(Long id) {
        Role role = getById(id);
        RoleDto roleDto = BeanCopyUtils.copyBean(role, RoleDto.class);
        return ResponseResult.okResult(roleDto);
    }

    @Override
    public ResponseResult update(RoleMoreDto roleMoreDto) {
        Role role = BeanCopyUtils.copyBean(roleMoreDto, Role.class);
        updateById(role);

        //删除
        List<Long> menuIds1 = roleMoreDto.getMenuIds();
        for (Long aLong : menuIds1) {
            LambdaQueryWrapper<RoleMenu> qw = new LambdaQueryWrapper<>();
            qw.eq(RoleMenu::getMenuId, aLong);

            roleMenuService.remove(qw);
        }

        //插入
        List<Long> menuIds = roleMoreDto.getMenuIds();
        List<RoleMenu> roleMenus = menuIds.stream()
                .map(m -> new RoleMenu(role.getId(), m))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(Long id) {
        this.getBaseMapper().deleteById(id);
        return ResponseResult.okResult();
    }


}

