package cn.knowei.sbg.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 18:36 2023/2/23
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;


}
