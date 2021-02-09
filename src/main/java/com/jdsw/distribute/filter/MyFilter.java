package com.jdsw.distribute.filter;


import com.google.common.base.Splitter;
import com.jdsw.distribute.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Order(1) //数字越小，越先执行此过滤器
@Component
@WebFilter(filterName = "MyFilter", urlPatterns = {"/**"}) // 过滤规则——所有
public class MyFilter implements Filter {



    @Value("${filter.config.excludeUrls}")
    private String excludeUrls; // 获取配置文件中不需要过滤的uri

    private List<String> excludes;

    // token验证失败时的响应消息
    private static final String VALID_ERROR = "{\"code\": \"6000\",\"msg\": \"token验证失败\"}";

    @Override
    public void init(FilterConfig filterConfig) {
        // 初始化
        // 移除配置文件中不过滤url，字符串的前空白和尾部空白
        excludes = Splitter.on(",").trimResults().splitToList(this.excludeUrls);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String uri = request.getRequestURI(); //获取请求uri
        try {
           /* if (this.isExcludesUrl(uri)) { // 判断请求uri是否需要过滤（方法在下面）
                chain.doFilter(req, resp); // 不需要，放行
            } else {
            String token = request.getHeader("token"); // 获取头中token
                if (!validateParams(token)) { // 验证头中的token（方法在下面）
                    response.setContentType("text/xml;charset=UTF-8");
                    response.getWriter().write(VALID_ERROR); // 验证失败，返回验证失败消息
                    return;
                }
                chain.doFilter(req, resp); // 验证成功，放行
            }*/
            chain.doFilter(req, resp); // 验证成功，放行
        } catch (Exception ex) {
            //log.error("Exception error", ex);
            response.setContentType("text/xml;charset=UTF-8");
            response.getWriter().write(VALID_ERROR); // 抛出异常，返回验证失败消息
        } finally {
            response.flushBuffer(); // 将缓冲信息输出到页面
        }

    }

    private boolean validateParams(String token) {
        // 验证是否为空，格式是否满足预定要求
        if (!StringUtils.isEmpty(token)) {
            Map<String, Object> map = JwtUtil.parseJWT(token);// 掉用jwt解密方法解密
            if (map != null) {
                return true;  // 解密成功，返回true
            }
        }
        return false; // 解密失败，返回false
    }

    private boolean isExcludesUrl(String path) {
        for (String v : this.excludes) {
            if (path.substring(0,2).equals("/K") || path.substring(0,2).equals("/L") || path.substring(0,2).equals("/Z")){
                return true;
            }
            if (path.startsWith(v)) {// 判断请求uri 是否满足配置文件uri要求
                return true;  // 满足、也就是请求uri 为 登录、注册，返回true
            }
        }
        return false; // 不满足、也就是请求uri 不是登录、注册，返回false
    }
}