package com.jdsw.distribute.controller;

import com.github.pagehelper.util.StringUtil;
import com.jdsw.distribute.util.Message;
import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.model.User;
import com.jdsw.distribute.service.UserService;
import com.jdsw.distribute.vo.UsersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/userList")
    public String userList(){
        return "/user/userList";
    }

    @RequestMapping("/insertUser")
    public Message insertUser(@RequestBody User user){
        int i = userService.insertUser(user);
        if (i <= 0 ){
            return Message.fail("添加失败");
        }else if(i == 3){
            return Message.fail("已存在相同账号");
        }else {
            return Message.success("添加成功");
        }
    }
    @RequestMapping("/userStatistics")
    public String userStatistics(){
        return "/user/userStatistics";
    }
    @RequestMapping(value = "/userJson")
    public PageInfo<UsersVo> userJson(HttpServletRequest request, HttpServletResponse response, Integer currentPage, Integer pageSize) {
        PageInfo<UsersVo> pageInfo = userService.findAllUser(currentPage,pageSize);
        return pageInfo;
    }
    /**
     * 查询多部门下的人员
     * @param department
     * @return
     */
    @RequestMapping("/queryDepartment")
    public Message queryDepartment(String department){
        return Message.success("操作成功",userService.queryDepartment(department));
    }

}
