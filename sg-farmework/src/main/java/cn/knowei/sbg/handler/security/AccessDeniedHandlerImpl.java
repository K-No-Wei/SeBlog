package cn.knowei.sbg.handler.security;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.enums.AppHttpCodeEnum;
import cn.knowei.sbg.utils.WebUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 23:06 2023/2/23
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        e.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);

        //响应
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
