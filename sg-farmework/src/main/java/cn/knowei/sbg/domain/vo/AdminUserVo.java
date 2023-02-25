package cn.knowei.sbg.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 10:57 2023/2/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserVo {
    private String userName;
    private String password;
}
