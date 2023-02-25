package cn.knowei.sbg.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 20:02 2023/2/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleLessDto {
    private String roleId;
    private String status;
}
