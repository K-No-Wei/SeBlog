package cn.knowei.sbg.service.impl;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.UserDto;
import cn.knowei.sbg.domain.dto.UserListDto;
import cn.knowei.sbg.domain.vo.AdUserListVo;
import cn.knowei.sbg.domain.vo.PageVo;
import cn.knowei.sbg.domain.vo.UserInfoVo;
import cn.knowei.sbg.domain.vo.UserRoleVo;
import cn.knowei.sbg.entity.Role;
import cn.knowei.sbg.entity.User;
import cn.knowei.sbg.entity.UserRole;
import cn.knowei.sbg.enums.AppHttpCodeEnum;
import cn.knowei.sbg.exception.SystemException;
import cn.knowei.sbg.mapper.UserMapper;
import cn.knowei.sbg.service.RoleService;
import cn.knowei.sbg.service.UserRoleService;
import cn.knowei.sbg.service.UserService;
import cn.knowei.sbg.utils.BeanCopyUtils;
import cn.knowei.sbg.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.minidev.json.writer.BeansMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.nio.channels.ReadPendingException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-02-23 18:23:46
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult userInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //非空判断
        if (!StringUtils.hasText(user.getUsername())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //存在
        if (!checkExistName(user.getUsername())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }

        if (!checkExistNick(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }

        //加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult list(Long pageNum, Long pageSize, UserDto userDto) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(StringUtils.hasText(userDto.getPhonenumber()), User::getPhonenumber, userDto.getPhonenumber());
        qw.eq(StringUtils.hasText(userDto.getStatus()), User::getStatus, userDto.getStatus());

        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, qw);
        List<AdUserListVo> adUserListVos = BeanCopyUtils.copyBeanList(page.getRecords(), AdUserListVo.class);
        for (int i = 0; i < adUserListVos.size(); i++) {
            adUserListVos.get(i).setUserName(page.getRecords().get(i).getUsername());
        }


        return ResponseResult.okResult(new PageVo(adUserListVos, page.getTotal()));
    }

    @Override
    @Transactional
    public ResponseResult add(UserListDto userListDto) {
        User user = BeanCopyUtils.copyBean(userListDto, User.class);
        user.setUsername(userListDto.getUserName());

        String encodePass = passwordEncoder.encode(userListDto.getPassword());
        user.setPassword(encodePass);
        save(user);


        List<Long> roleIds = userListDto.getRoleIds();
        List<UserRole> userRoles = roleIds.stream()
                .map(m -> new UserRole(user.getId(), m))
                .collect(Collectors.toList());

        userRoleService.saveBatch(userRoles);


        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getOne(Long id) {
        List<UserRole> userRoles = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, id));
        List<Long> roleIds = userRoles.stream()
                .map(m -> m.getRoleId())
                .collect(Collectors.toList());

        User user = getById(id);

        List<Role> roles = roleService.list();

        return ResponseResult.okResult(new UserRoleVo(roleIds, roles, user));

    }

    @Override
    @Transactional
    public ResponseResult update(UserListDto userListDto) {

        userRoleService.remove(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userListDto.getId()));

        List<UserRole> userRoles = userListDto.getRoleIds().stream()
                .map(m -> new UserRole(userListDto.getId(), m))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);

        User user = BeanCopyUtils.copyBean(userListDto, User.class);
        updateById(user);

        return ResponseResult.okResult();
    }

    private boolean checkExistNick(String nickName) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getNickName, nickName);
        User user = getOne(qw);
        return Objects.isNull(user);
    }

    private boolean checkExistName(String userName) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getUsername, userName);
        User user = getOne(qw);
        return Objects.isNull(user);
    }


}

