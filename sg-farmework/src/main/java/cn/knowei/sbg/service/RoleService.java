package cn.knowei.sbg.service;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.RoleDto;
import cn.knowei.sbg.domain.dto.RoleLessDto;
import cn.knowei.sbg.domain.dto.RoleMoreDto;
import cn.knowei.sbg.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-02-25 11:26:23
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long userId);

    ResponseResult list(Long pageNum, Long pageSize, RoleDto roleDto);

    ResponseResult changeStatus(RoleLessDto roleDto);

    ResponseResult add(RoleMoreDto roleMoreDto);

    ResponseResult getOne(Long id);

    ResponseResult update(RoleMoreDto roleMoreDto);

    ResponseResult delete(Long id);

    ResponseResult listAllRole();
}

