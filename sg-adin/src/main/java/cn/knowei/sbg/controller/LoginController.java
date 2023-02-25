package cn.knowei.sbg.controller;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.vo.*;
import cn.knowei.sbg.entity.Menu;
import cn.knowei.sbg.entity.User;
import cn.knowei.sbg.enums.AppHttpCodeEnum;
import cn.knowei.sbg.exception.SystemException;
import cn.knowei.sbg.service.BlogLoginService;
import cn.knowei.sbg.service.LoginService;
import cn.knowei.sbg.service.MenuService;
import cn.knowei.sbg.service.RoleService;
import cn.knowei.sbg.utils.BeanCopyUtils;
import cn.knowei.sbg.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 18:20 2023/2/23
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService LoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    /**
     * 登入接口
     * @return
     */
    @PostMapping("user/login")
    public ResponseResult login(@RequestBody AdminUserVo adminUserVo){
        if(!StringUtils.hasText(adminUserVo.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        User user = new User();
        user.setUsername(adminUserVo.getUserName());
        user.setPassword(adminUserVo.getPassword());
        return LoginService.login(user);
    }

    @GetMapping("getInfo")
    public ResponseResult getInfo(){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUser().getId();
        // 获取权限菜单
        List<String> perms = menuService.selectPermsByUserId(userId);
        // 获取角色
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(userId);
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);

    }

    /**
     * 查找所有菜单
     * @return
     */
    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        // Map<String, List<Menu>> map = new HashMap<>();
        // map.put("menus", menus);
        return ResponseResult.okResult(new RoutersVo(menus));
    }

    /**
     * 注销窗口
     * @return
     */
    @PostMapping("user/logout")
    public ResponseResult logout(){
        return LoginService.logout();
    }
}
