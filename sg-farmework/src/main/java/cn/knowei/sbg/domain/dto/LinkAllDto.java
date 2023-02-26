package cn.knowei.sbg.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 8:12 2023/2/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkAllDto {
    private Long id;
    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;
    private String status;

}
