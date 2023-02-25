package cn.knowei.sbg.controller;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.vo.UserVo;
import cn.knowei.sbg.entity.User;
import cn.knowei.sbg.enums.AppHttpCodeEnum;
import cn.knowei.sbg.exception.SystemException;
import cn.knowei.sbg.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 18:20 2023/2/23
 */
@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    /**
     * 登入接口
     * @return
     */
    @PostMapping("login")
    public ResponseResult login(@RequestBody UserVo userVo){
        if(!StringUtils.hasText(userVo.getUsername())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        User user = new User();
        user.setUsername(userVo.getUsername());
        user.setPassword(userVo.getPassword());
        return blogLoginService.login(user);
    }

    /**
     * 注销窗口
     * @return
     */
    @PostMapping("logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
