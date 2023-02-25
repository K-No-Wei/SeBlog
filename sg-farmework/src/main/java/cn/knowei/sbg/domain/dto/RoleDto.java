package cn.knowei.sbg.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 19:52 2023/2/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    //角色ID@TableId
    private Long id;

    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
    private String remark;
}
