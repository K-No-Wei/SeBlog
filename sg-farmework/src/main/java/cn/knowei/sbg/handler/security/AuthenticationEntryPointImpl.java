package cn.knowei.sbg.handler.security;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.enums.AppHttpCodeEnum;
import cn.knowei.sbg.utils.WebUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.naming.InsufficientResourcesException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 23:01 2023/2/23
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();

        //异常类型判断
        ResponseResult result = null;
        if (e instanceof BadCredentialsException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), e.getMessage());
        }else if(e instanceof InsufficientAuthenticationException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else {
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, "认证活授权失败");
        }
        //转换json格式
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
