package com.jdsw.distribute.filter;

import com.jdsw.distribute.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String headToken = request.getHeader("token"); // 获取头中token
        // 验证是否为空，格式是否满足预定要求
        if (!StringUtils.isEmpty(headToken)) {
            Map<String, Object> map = JwtUtil.parseJWT(headToken); // 掉用jwt解密方法解密
            if (map != null) {
                request.setAttribute("username", map.get("userName"));
                request.setAttribute("name",map.get("name"));
                return true;
            }
        }
        return false;
    }
}