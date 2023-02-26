package cn.knowei.sbg.domain.vo;

import cn.knowei.sbg.entity.Role;
import cn.knowei.sbg.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 9:01 2023/2/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleVo {
    private List<Long> roleIds;

    private List<Role> roles;

    private User user;
}
