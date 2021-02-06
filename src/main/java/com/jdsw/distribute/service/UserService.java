package com.jdsw.distribute.service;

import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.model.Enterprise;
import com.jdsw.distribute.model.Role;
import com.jdsw.distribute.model.User;
import com.jdsw.distribute.vo.UsersVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {
    PageInfo<UsersVo> findAllUser(int pageNum, int pageSize);
    public User findByUserName(String username);
    Set<String> findPermissionByUserName(String username);
    int countUser();
    int insertUser(User user);
    Set<String> findRoleByUserName(String username);
    Set<String> findRoleByUserName2(String username);
    /**
     * 查询部门下的人员
     * @param
     * @param
     * @return
     */
    List<User> queryDepartment(Map map);

    /**
     * 查询组下的人员
     * @param
     * @return
     */
    List<User> queryGroup(String group,String branch);
    /**
     * 查询部门主管
     * @return
     */
    List<UsersVo> queryCharge(String department,String branch,String group);

    UsersVo queryBranch(String username);

    List<Enterprise> queryEnterprise(String corporatePhone);



}
