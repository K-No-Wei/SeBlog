package cn.knowei.sbg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 10:34 2023/2/25
 */
@SpringBootApplication
@MapperScan("cn.knowei.sbg.mapper")
public class SGSpringAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(SGSpringAdminApplication.class);
    }
}
