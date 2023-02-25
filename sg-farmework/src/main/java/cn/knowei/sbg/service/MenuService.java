package cn.knowei.sbg.service;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.MenuDto;
import cn.knowei.sbg.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-02-25 11:22:26
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult list(MenuDto menuDto);

    ResponseResult add(Menu menu);

    ResponseResult update(Menu menu);

    ResponseResult delete(Long menuId);

    ResponseResult treeselect();

    ResponseResult roleMenuTreeselect(Long id);
}

