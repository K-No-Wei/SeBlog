package cn.knowei.sbg.service.impl;

import cn.knowei.sbg.domain.vo.LoginUser;
import cn.knowei.sbg.entity.User;
import cn.knowei.sbg.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 18:38 2023/2/23
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //传入有用吗密码进行判断
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getUsername, s);

        User user = userMapper.selectOne(qw);

        // 无此用户
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }

        //查询权限信息

        return new LoginUser(user);
    }
}