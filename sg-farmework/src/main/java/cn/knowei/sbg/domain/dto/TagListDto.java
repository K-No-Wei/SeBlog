package cn.knowei.sbg.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 15:17 2023/2/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListDto {
    private Long id;
    private String name;
    private String remark;
}
