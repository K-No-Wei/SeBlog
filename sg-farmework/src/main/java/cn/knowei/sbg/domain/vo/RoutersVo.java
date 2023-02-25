package cn.knowei.sbg.domain.vo;

import cn.knowei.sbg.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 13:45 2023/2/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {
    private List<Menu> menus;
}
