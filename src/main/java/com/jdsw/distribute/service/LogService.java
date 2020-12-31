/*

package com.mys.mbackstage.service;

import com.mys.mbackstage.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@Component
@Aspect
public class LogService {
    public LogService() {
        System.out.println("Aop");
    }


*/
/**
     * 切点
     *//*


    @Pointcut("@annotation(com.mysmbackstage.logaop.MethodLog)")
    public void methodCachePointcut() {
    }


*/
/**
     * 切面
     *
     * @param point
     * @return
     * @throws Throwable
     *//*


    @Around("methodCachePointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
        Calendar ca = Calendar.getInstance();
        String operDate = df.format(ca.getTime());
        String ip = getIp(request);
        User user = UserUtil.getCurrentUser(request);
        String loginName;
        String name;
        if (user != null) {
            loginName = user.getLoginName();
        } else {
            loginName = "匿名用户";
        }
        String methodRemark = getMthodRemark(point);
        String methodName = point.getSignature().getName();
        String packages = point.getClass().getName();
        if (packages.indexOf("$$EnhancerByCGLIB$$") > -1) { // 如果是CGLIB动态生成的类
            try {
                packages = packages.substring(0, packages.indexOf("$$"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        String operatingcontent = "";
        Object[] method_param = null;

        Object object;
        try {
            method_param = point.getArgs(); //获取方法参数
            // String param=(String) point.proceed(point.getArgs());
            object = point.proceed();
        } catch (Exception e) {
            // 异常处理记录日志..log.error(e);
            throw e;
        }

        Syslog sysLog = new Syslog();
        sysLog.setOptId(org.triber.common.util.StringUtil.getRandNum(11));
        sysLog.setLoginId(user.getLoginId());
        sysLog.setLoginName(loginName);
        sysLog.setIpAddress(ip);
        sysLog.setMethodName(packages + "." + methodName);
        sysLog.setMethodRemark(methodRemark);
        sysLog.setOptDate(operDate);
    }
}

*/
