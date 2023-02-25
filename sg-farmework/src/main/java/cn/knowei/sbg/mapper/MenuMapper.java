package cn.knowei.sbg.mapper;

import cn.knowei.sbg.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-25 11:22:26
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectAllMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}

