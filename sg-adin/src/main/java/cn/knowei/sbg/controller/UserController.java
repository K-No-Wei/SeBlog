package cn.knowei.sbg.controller;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.UserDto;
import cn.knowei.sbg.domain.dto.UserListDto;
import cn.knowei.sbg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 7:40 2023/2/26
 */
@RestController
@RequestMapping("system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("list")
    public ResponseResult list(Long pageNum, Long pageSize, UserDto userDto){
        return userService.list(pageNum, pageSize, userDto);
    }

    @PostMapping()
    public ResponseResult add(@RequestBody UserListDto userListDto){
        return userService.add(userListDto);
    }

    @DeleteMapping("{id}")
    public ResponseResult delete(@PathVariable Long id){
        userService.getBaseMapper().deleteById(id);

        return ResponseResult.okResult();
    }

    @GetMapping("{id}")
    public ResponseResult getOne(@PathVariable Long id){
        return userService.getOne(id);
    }

    @PutMapping()
    public ResponseResult update(@RequestBody UserListDto userListDto){
        return userService.update(userListDto);
    }
}
