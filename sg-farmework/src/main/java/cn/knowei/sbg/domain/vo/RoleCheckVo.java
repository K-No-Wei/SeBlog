package cn.knowei.sbg.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 21:29 2023/2/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleCheckVo {
    private List<RoleMenuVo> menus;
    private List<Long> checkedKeys;
}
