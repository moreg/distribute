package com.jdsw.distribute.controller;

import com.github.pagehelper.util.StringUtil;
import com.jdsw.distribute.util.JwtUtil;
import com.jdsw.distribute.util.Message;
import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.model.User;
import com.jdsw.distribute.service.UserService;
import com.jdsw.distribute.vo.UsersVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    /**
     * 修改密码
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/updatePassword")
    public Message updatePassword(@RequestBody UsersVo user,HttpServletRequest request){
        String username = (String) request.getAttribute("username");
        user.setUsername(username);
        int i = userService.updatePassword(user);
        if (i == 2){
            return Message.warn("密码不正确");
        }else if (i == 3){
            return Message.warn("两次密码不一致");
        }
        return Message.success("修改成功");
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
     * 查询部门下的人员
     * @param department
     * @return
     */
    @RequestMapping("/queryDepartment")
    public Message queryDepartment(String department,String branch,String group,HttpServletRequest request){
/*        if (StringUtils.isEmpty(department) && StringUtils.isEmpty(branch)){
            return Message.fail("参数不能为空");
        }*/
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map map1 = new HashMap();
        map1.put("name",name);
        map1.put("username",username);
        map1.put("department",department);
        map1.put("branch",branch);
        map1.put("group",group);
        return Message.success("操作成功",userService.queryDepartment(map1));
    }
    /**
     * 查询部门下的人员
     * @param
     * @return
     */
    @RequestMapping("/queryGroup")
    public Message queryGroup(String group,String branch){
/*        if (StringUtils.isEmpty(department) && StringUtils.isEmpty(branch)){
            return Message.fail("参数不能为空");
        }*/
        return Message.success("操作成功",userService.queryGroup(group,branch));
    }
    /**
     * 获取部门主管
     * @param department
     * @return
     */
    @RequestMapping("/queryCharge")
    public Message queryCharge(String department,String branch,String group){
/*        if (StringUtils.isEmpty(department) && StringUtils.isEmpty(branch)){
            return Message.fail("参数不能为空");
        }*/
        return Message.success("操作成功",userService.queryCharge(department,branch,group));
    }

    /**
     * 查询企业信息
     * @return
     */
    @RequestMapping("/queryEnterprise")
    public Message queryEnterprise(String corporatePhone){
        return Message.success("操作成功",userService.queryEnterprise(corporatePhone));
    }

    /**
     * 获得角色权限
     * @param session
     * @return
     */
    @RequestMapping("/queryRoles")
    public Message queryRoles(HttpSession session){
        String username = (String) session.getAttribute("username");
        Map map = new HashMap();
        map.put("permission",userService.findPermissionByUserName(username));
        map.put("role",userService.findRoleByUserName(username));
        return Message.success("操作成功",map);
    }


}
