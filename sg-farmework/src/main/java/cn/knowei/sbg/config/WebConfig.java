package cn.knowei.sbg.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author: knowei
 * @Description: 跨域配置
 * @Date: Create in 16:17 2023/2/23
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    // @Bean//使用@Bean注入fastJsonHttpMessageConvert
    // public HttpMessageConverter fastJsonHttpMessageConverters() {
    //     //1.需要定义一个Convert转换消息的对象
    //     FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
    //     FastJsonConfig fastJsonConfig = new FastJsonConfig();
    //     fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
    //     fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
    //
    //     SerializeConfig.globalInstance.put(Long.class, ToStringSerializer.instance);
    //
    //     fastJsonConfig.setSerializeConfig(SerializeConfig.globalInstance);
    //     fastConverter.setFastJsonConfig(fastJsonConfig);
    //     HttpMessageConverter<?> converter = fastConverter;
    //     return converter;
    // }
    //
    // @Override
    // public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    //     converters.add(fastJsonHttpMessageConverters());
    // }
}
