package cn.knowei.sbg.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 19:15 2023/2/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {
    private String status;
    private String menuName;
}
