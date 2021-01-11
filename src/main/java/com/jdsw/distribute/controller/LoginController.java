package com.jdsw.distribute.controller;

import com.jdsw.distribute.model.User;
import com.jdsw.distribute.service.UserService;
import com.jdsw.distribute.util.JwtUtil;
import com.jdsw.distribute.util.Message;
import com.jdsw.distribute.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
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
    public Message login(HttpSession session, HttpServletRequest request , @RequestBody User user) {
        String msg = "";
        String username = user.getUsername();
        String pwd = user.getPassword();
        UsernamePasswordToken token=new UsernamePasswordToken(username, pwd);
        token.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        try {
            User user2 = userService.findByUserName(username);
            subject.login(token);
            session.setAttribute("username",username);
            session.setAttribute("name",user2.getName());
            String toToken = JwtUtil.sign(username,pwd);

            List li = new ArrayList();
            li = menuService.getMenuLsit();
            Map map = new HashMap();
            map.put("token",toToken);
            //map.put("permission",userService.findPermissionByUserName(username));
            map.put("role",userService.findRoleByUserName(username));
            map.put("userId",user2.getId());
            map.put("name",user2.getName());
            map.put("username",username);
            //map.put("menus",li);
            return Message.success("登录成功",map);
        }  catch (IncorrectCredentialsException e) {
            msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.";
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
        } catch (UnknownAccountException e) {
            msg = "帐号不存在. There is no user with username of " + token.getPrincipal();
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！" + e.getMessage();
        }
        //request.setAttribute("msg",msg);
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
