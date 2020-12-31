package com.jdsw.distribute.service;

import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.model.Role;
import com.jdsw.distribute.model.User;
import com.jdsw.distribute.vo.UsersVo;

import java.util.List;
import java.util.Set;

public interface UserService {
    PageInfo<UsersVo> findAllUser(int pageNum, int pageSize);
    public User findByUserName(String username);
    Set<String> findPermissionByUserName(String username);
    int countUser();
    int insertUser(User user);
    Set<String> findRoleByUserName(String username);

    List<UsersVo> queryDepartment(String department);


}
