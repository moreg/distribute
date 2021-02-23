package com.jdsw.distribute.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdsw.distribute.model.Menu;
import com.jdsw.distribute.model.SysMune;
import com.jdsw.distribute.model.User;
import com.jdsw.distribute.service.MenuService;

import com.jdsw.distribute.service.UserService;
import com.jdsw.distribute.util.JwtUtil;
import com.jdsw.distribute.util.Message;
import com.jdsw.distribute.vo.UsersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;

    /**
     * 主菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "/menuList")
    public Message menuLsit(HttpServletRequest request){
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map1 = JwtUtil.parseJWT(token);
        String username = (String) map1.get("userName");
        User user2 = userService.findByUserName(username);
        //List<SysMune> menu = menuService.findTree(username);
        List<SysMune> menuslist = menuService.getMenuLsit(username);
        Map map = new HashMap<>();
        map.put("menus",menuslist);
        map.put("userId",user2.getId());
        map.put("username",username);
        return Message.success("查询成功",map);
    }

    /**
     * 所有分公司菜单
     * @return
     */
    @RequestMapping("/getBranch")
    public Message getBranch(){
         return Message.success("操作成功",menuService.findBranch());
    }

    /**
     * 根据分公司获得分公司的部门
     * @param
     * @return
     */
    @RequestMapping("getSection")
    public Message getSection(String id){
        return Message.success("操作成功",menuService.getSection(id));
    }

    @RequestMapping("/getSubordinate")
    public Message getSubordinate(HttpServletRequest request){
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map mapl = new HashMap();
        mapl.put("username",username);
        mapl.put("name",name);
        return Message.success("查询成功",menuService.getSubordinate(mapl));
    }

    /**
     *  下属客户
     * @param branch
     * @param request
     * @return
     */
    @RequestMapping("/getsubordinateMenuList")
    public Message getsubordinateMenuList(String branch,HttpServletRequest request){
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        Map map2 = new HashMap();
        map2.put("username",username);
        map2.put("branch",branch);

        return Message.success("查询成功",menuService.getsubordinateMenuList(map2));
    }

    /**
     * 办理业务菜单
     * @return
     */
    @RequestMapping("/businessMenu")
    public Message businessMenu(){
        return Message.success("查询成功",menuService.businessMenu());
    }
    /**
     * 来源
     * @return
     */
    @RequestMapping("/kSourceMenu")
    public Message kSourceMenu(String type){
        return Message.success("查询成功",menuService.kSourceMenu(type));
    }
}
