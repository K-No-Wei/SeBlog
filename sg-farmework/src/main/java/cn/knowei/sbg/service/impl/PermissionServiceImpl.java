package cn.knowei.sbg.service.impl;

import cn.knowei.sbg.entity.Menu;
import cn.knowei.sbg.service.MenuService;
import cn.knowei.sbg.service.PermissionService;
import cn.knowei.sbg.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 18:31 2023/2/25
 */
@Service("ps")
public class PermissionServiceImpl implements PermissionService {

    /**
     * 判断当前用户是否具有permission
     * @param permission
     * @return
     */
    public boolean hasPermission(String permission){
        //超级管理员允许
        if (SecurityUtils.isAdmin()){
            return true;
        }

        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
