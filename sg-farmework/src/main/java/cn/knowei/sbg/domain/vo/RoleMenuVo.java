package cn.knowei.sbg.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 20:21 2023/2/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RoleMenuVo {
    private Long id;
    private String label;
    private Long parentId;
    private List<RoleMenuVo> children;

}
