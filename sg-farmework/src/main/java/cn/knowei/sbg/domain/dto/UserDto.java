package cn.knowei.sbg.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 7:43 2023/2/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String status;
    //手机号
    private String phonenumber;
}
