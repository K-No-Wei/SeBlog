package cn.knowei.sbg.mapper;

import cn.knowei.sbg.domain.dto.RoleLessDto;
import cn.knowei.sbg.domain.vo.RoleMenuVo;
import cn.knowei.sbg.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-25 11:26:23
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);

    void updateStatusByRoleId(RoleLessDto roleLessDto);

    List<RoleMenuVo> selectAllRoleMenu();

    List<RoleMenuVo> selectAllRoleMenuById(Long id);
}

