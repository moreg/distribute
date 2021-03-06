package com.jdsw.distribute.controller;

import com.jdsw.distribute.model.User;
import com.jdsw.distribute.service.UserService;
import com.jdsw.distribute.util.JwtUtil;
import com.jdsw.distribute.util.Message;
import com.jdsw.distribute.service.MenuService;
import com.jdsw.distribute.vo.UsersVo;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@Controller
public class LoginController {
    private static Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;
    @RequestMapping("/index")
    public String index(){
        return "/index";
    }
    @RequestMapping("/login")
    @ResponseBody
    public Message login(HttpSession session, HttpServletRequest request , @RequestBody User user, HttpServletResponse response) {

        String msg = "";
        String username = user.getUsername();
        String pwd = user.getPassword();
        UsernamePasswordToken token=new UsernamePasswordToken(username, pwd);
        token.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        try {
            User user2 = userService.findByUserName(username);
            subject.login(token);
            subject.hasRole("登录");
            Set<String> roles = userService.findRoleByUserName2(username);
            String toToken = null;

            for (String role :roles){
                 toToken = JwtUtil.sign(username,user2.getId().toString(),user2.getName(),pwd,role);
            }
            UsersVo usersVo = userService.queryBranch(username);
            Map map = new HashMap();
            map.put("token",toToken);
            map.put("role",roles);
            map.put("userId",user2.getId());
            map.put("name",user2.getName());
            map.put("group",usersVo.getGroup());
            map.put("branch",usersVo.getBranch());
            if ("南宁分公司".equals(usersVo.getBranch())){
                map.put("branchId",14);
            }
            if ("梧州分公司".equals(usersVo.getBranch())){
                map.put("branchId",15);
            }
            map.put("department",usersVo.getDepartment());
            map.put("username",username);
            return Message.success("登录成功",map);
        }  catch (IncorrectCredentialsException e) {
            msg = "登录密码错误";
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定";
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用";
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期";
        } catch (UnknownAccountException e) {
            msg = "帐号不存在";
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！";
        }
        return Message.fail(msg);


    }

    /**
     * 退出
     * @param session
     * @return
     */
    @RequestMapping("/logOut")
    public String logOut(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
//        session.removeAttribute("user");
        return "login";
    }

}
