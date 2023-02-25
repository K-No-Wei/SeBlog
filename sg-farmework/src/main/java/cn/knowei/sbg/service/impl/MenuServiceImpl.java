package cn.knowei.sbg.service.impl;

import cn.knowei.sbg.constants.SystemConstants;
import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.MenuDto;
import cn.knowei.sbg.domain.vo.RoleCheckVo;
import cn.knowei.sbg.domain.vo.RoleMenuVo;
import cn.knowei.sbg.entity.Menu;
import cn.knowei.sbg.enums.AppHttpCodeEnum;
import cn.knowei.sbg.exception.SystemException;
import cn.knowei.sbg.mapper.MenuMapper;
import cn.knowei.sbg.mapper.RoleMapper;
import cn.knowei.sbg.mapper.TagMapper;
import cn.knowei.sbg.service.MenuService;
import cn.knowei.sbg.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-02-25 11:22:26
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<String> selectPermsByUserId(Long userId) {
        if (SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> qw = new LambdaQueryWrapper<>();
            qw.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            qw.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(qw);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        return getBaseMapper().selectPermsByUserId(userId);
    }

    /**
     * 返回所有菜单
     * @param userId
     * @return
     */
    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        List<Menu> menus;
        if (SecurityUtils.isAdmin()){
            // 管理员拥有全部菜单
            menus = menuMapper.selectAllMenu();
        }else {
            // 当前用户具有的菜单
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        // 主菜单
        // 第一层，第二层
        List<Menu> menuTree = builderMenuTree(menus, 0L);
        return menuTree;
    }

    @Override
    public ResponseResult list(MenuDto menuDto) {
        LambdaQueryWrapper<Menu> qw = new LambdaQueryWrapper<>();
        qw.eq(StringUtils.hasText(menuDto.getMenuName()), Menu::getMenuName, menuDto.getMenuName());
        qw.eq(StringUtils.hasText(menuDto.getStatus()), Menu::getStatus, menuDto.getStatus());

        List<Menu> menus = list(qw);
        return ResponseResult.okResult(menus);

    }

    @Override
    public ResponseResult add(Menu menu) {
        save(menu);

        return ResponseResult.okResult();
    }

    /**
     * 修改菜单，菜单不能为自己的父菜单
     * @param menu
     * @return
     */
    @Override
    public ResponseResult update(Menu menu) {
        if (menu.getId().equals(menu.getParentId())){
            throw new SystemException(AppHttpCodeEnum.MENU_ERROR);
        }

        update(menu);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(Long menuId) {
        int count = count(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, menuId));
        if (count > 0){
            throw new SystemException(AppHttpCodeEnum.MENU_CHILD_EXIST);
        }
        removeById(menuId);
        return ResponseResult.okResult();
    }

    @Autowired
    private RoleMapper roleMapper;
    @Override
    public ResponseResult treeselect() {
        List<RoleMenuVo> roleMenuVos = roleMapper.selectAllRoleMenu();

        roleMenuVos = toTreeRoleMenu(roleMenuVos, 0L);
        return ResponseResult.okResult(roleMenuVos);
    }

    @Override
    public ResponseResult roleMenuTreeselect(Long id) {
        //获取全部菜单
        List<RoleMenuVo> allRoleMenuVos = roleMapper.selectAllRoleMenu();
        List<RoleMenuVo> roleMenuVos;
        if (id == 1){
            roleMenuVos = allRoleMenuVos;
        }else {
            roleMenuVos = roleMapper.selectAllRoleMenuById(id);
        }


        allRoleMenuVos = toTreeRoleMenu(allRoleMenuVos, 0L);
        List<Long> checkedKeys = roleMenuVos.stream()
                .map(m -> m.getId())
                .collect(Collectors.toList());
        return ResponseResult.okResult(new RoleCheckVo(allRoleMenuVos, checkedKeys));
    }

    private List<RoleMenuVo> toTreeRoleMenu(List<RoleMenuVo> roleMenuVos, long parentId) {
        return roleMenuVos.stream()
                .filter(roleMenuVo -> roleMenuVo.getParentId().equals(parentId))
                .map(roleMenuVo -> roleMenuVo.setChildren(getChildren(roleMenuVos, roleMenuVo)))
                .collect(Collectors.toList());
    }

    private List<RoleMenuVo> getChildren(List<RoleMenuVo> roleMenuVos, RoleMenuVo roleMenuVo) {
        return roleMenuVos.stream()
                .filter(m -> m.getParentId().equals(roleMenuVo.getId()))
                .map(m -> m.setChildren(getChildren(roleMenuVos, m)))
                .collect(Collectors.toList());

    }

    /**
     * 胡获取第一层菜单
     * @param menus
     * @param parentId
     * @return
     */
    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {

        //
        List<Menu> menuTree = menus.stream()
                // 过滤
                .filter(menu -> menu.getParentId().equals(parentId))
                // 设置子菜单
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取传入参数的子菜单
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus){
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(me -> me.setChildren(getChildren(me, menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}

