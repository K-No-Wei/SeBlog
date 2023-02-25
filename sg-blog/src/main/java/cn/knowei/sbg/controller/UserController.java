package cn.knowei.sbg.controller;

import cn.knowei.sbg.annotation.SystemLog;
import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.entity.User;
import cn.knowei.sbg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 20:35 2023/2/24
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PutMapping("userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("register")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}
