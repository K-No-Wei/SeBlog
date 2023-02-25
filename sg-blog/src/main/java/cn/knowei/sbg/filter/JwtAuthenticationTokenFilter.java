package cn.knowei.sbg.filter;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.vo.LoginUser;
import cn.knowei.sbg.enums.AppHttpCodeEnum;
import cn.knowei.sbg.utils.JwtUtil;
import cn.knowei.sbg.utils.RedisCache;
import cn.knowei.sbg.utils.WebUtils;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 19:40 2023/2/23
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            //没有携带token，直接放行
            filterChain.doFilter(request, response);
            return;
        }

        //获取解析userid
        Claims claims = null;
        try{
            claims = JwtUtil.parseJWT(token);
        }catch (Exception e){
            e.printStackTrace();
            //非法token
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }

        //获取信息
        String userId = claims.getSubject();
        LoginUser loginUser = redisCache.getCacheObject("bloglogin:" + userId);
        if (Objects.isNull(loginUser)){
            //token已经过期
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }

        //存入SecurityContextHolder中，方便以后取出
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);

    }
}
