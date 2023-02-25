package cn.knowei.sbg.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 19:36 2023/2/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    String username;
    String password;
}
