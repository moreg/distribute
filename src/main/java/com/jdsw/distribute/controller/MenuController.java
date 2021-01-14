package com.jdsw.distribute.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdsw.distribute.model.Menu;
import com.jdsw.distribute.model.User;
import com.jdsw.distribute.service.MenuService;

import com.jdsw.distribute.service.UserService;
import com.jdsw.distribute.util.Message;
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

    @RequestMapping(value = "/menujson")
    @ResponseBody
    public String menujson(HttpSession session){
        List<Menu> menu = menuService.getMenuLsit();
        String json = JSON.toJSONString(menu);
        JSONObject jsonMenu = new JSONObject();
        JSONObject jsonMenu2 = new JSONObject();
        jsonMenu2.put("code",200);
        jsonMenu2.put("message","操作成功");
        jsonMenu.put("status",jsonMenu2);
        jsonMenu.put("data",json);
        System.out.println(jsonMenu);
        return json.toString();
    }
    @RequestMapping(value = "/menuList")
    public Message menuLsit(HttpServletRequest request){
        String username = (String) request.getAttribute("username");
        User user2 = userService.findByUserName(username);
        List<Menu> menu = menuService.findTree("user");
        List<Menu> menuslist = menuService.getMenuLsit();
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

}
