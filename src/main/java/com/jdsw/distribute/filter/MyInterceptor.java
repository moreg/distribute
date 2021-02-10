package com.jdsw.distribute.filter;

import com.google.common.base.Splitter;
import com.jdsw.distribute.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
public class MyInterceptor implements HandlerInterceptor {
    @Value("${filter.config.excludeUrls}")
    private String excludeUrls; // 获取配置文件中不需要过滤的uri
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String headToken = request.getHeader("token"); // 获取头中token
        System.out.println("headToken"+headToken);
        String uri = request.getRequestURI();
        List<String> excludes = Splitter.on(",").trimResults().splitToList(this.excludeUrls);
        if (uri.substring(0,2).equals("/K")  || uri.substring(0,2).equals("/L") || uri.substring(0,2).equals("/Z")){
            return true;
        }
        for (int i = 0;i<excludes.size();i++){
            if (excludes.get(i).equals(uri)){
                return true;
            }
        }
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


