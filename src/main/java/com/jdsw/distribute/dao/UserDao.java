package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.Role;
import com.jdsw.distribute.model.User;
import com.jdsw.distribute.vo.UsersVo;

import javax.xml.ws.Service;
import java.util.List;
import java.util.Set;

public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<UsersVo> selectUsers();

    User findByUserName(String username);

    Set<String> findPermissionByUserName(String username);

    int countUser();

    Set<String> findRoleByUserName(String username);

    /**
     * 查询部门下的人员
     * @param department
     * @return
     */
    List<UsersVo> queryDepartment(List<String> department);
}