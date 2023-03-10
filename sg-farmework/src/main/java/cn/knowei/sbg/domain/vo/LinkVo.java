package cn.knowei.sbg.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 18:15 2023/2/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVo {
    private Long id;
    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;
}
