package cn.knowei.sbg.service.impl;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.vo.BlogUserLoginVo;
import cn.knowei.sbg.domain.vo.LoginUser;
import cn.knowei.sbg.domain.vo.UserInfoVo;
import cn.knowei.sbg.entity.User;
import cn.knowei.sbg.service.BlogLoginService;
import cn.knowei.sbg.service.LoginService;
import cn.knowei.sbg.utils.BeanCopyUtils;
import cn.knowei.sbg.utils.JwtUtil;
import cn.knowei.sbg.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 18:21 2023/2/23
 */
@Service
public class BlogLoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    //注入redis
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //封装
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //判断是否认证通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名密码错误");
        }
        //获取loginUser，工具userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //存入redis
        redisCache.setCacheObject("adminlogin:" + userId, loginUser);

        HashMap<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //获取token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        // redis中删除userId
        redisCache.deleteObject("adminblog:" + userId);
        return ResponseResult.okResult();
    }
}
