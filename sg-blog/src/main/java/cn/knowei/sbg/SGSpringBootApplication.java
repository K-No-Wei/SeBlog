package cn.knowei.sbg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 13:14 2023/2/23
 */

@SpringBootApplication
@MapperScan("cn.knowei.sbg.mapper")
//定时任务
@EnableScheduling
@EnableSwagger2
public class SGSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SGSpringBootApplication.class);
    }
}
