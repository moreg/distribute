package com.jdsw.distribute.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdsw.distribute.model.Menu;
import com.jdsw.distribute.service.MenuService;

import com.jdsw.distribute.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;


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
    public String menuLsit(HttpSession session, Model model){
      //  String username = session.getAttribute;
        List<Menu> menu = menuService.findTree("user");
        List<Menu> menuslist = menuService.getMenuLsit();
        model.addAttribute("menu",menuslist);
        model.addAttribute("menus",menu);
        model.addAttribute("abc","123456");
        return "/menu/sysMenu";
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
