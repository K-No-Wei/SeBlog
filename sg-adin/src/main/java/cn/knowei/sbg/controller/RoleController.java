package cn.knowei.sbg.controller;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.RoleDto;
import cn.knowei.sbg.domain.dto.RoleLessDto;
import cn.knowei.sbg.domain.dto.RoleMoreDto;
import cn.knowei.sbg.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 19:51 2023/2/25
 */
@RestController
@RequestMapping("system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("list")
    public ResponseResult list(Long pageNum, Long pageSize, RoleDto roleDto){
        return roleService.list(pageNum, pageSize, roleDto);
    }

    @PutMapping("changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleLessDto roleLessDto){
        return roleService.changeStatus(roleLessDto);
    }

    @PostMapping()
    public ResponseResult add(@RequestBody RoleMoreDto roleMoreDto){
        return roleService.add(roleMoreDto);
    }

    @GetMapping("{id}")
    public ResponseResult getOne(@PathVariable Long id){
        return roleService.getOne(id);
    }

    @PutMapping()
    public ResponseResult update(@RequestBody RoleMoreDto roleMoreDto){
        return roleService.update(roleMoreDto);
    }

    @DeleteMapping("{id}")
    public ResponseResult delete(@PathVariable Long id){
        return roleService.delete(id);
    }

    @GetMapping("listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }
}
