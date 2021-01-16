package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.Enterprise;
import com.jdsw.distribute.model.Role;
import com.jdsw.distribute.model.User;
import com.jdsw.distribute.vo.UsersVo;
import org.apache.ibatis.annotations.Param;

import javax.xml.ws.Service;
import java.util.List;
import java.util.Map;
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
    List<User> queryDepartment(List<String> department);

    /**
     * 查询部门主管
     * @param
     * @return
     */
    List<UsersVo> queryCharge(List<String> department);

    /**
     * 查询企业信息
     * @param
     * @return
     */
    List<Enterprise> queryEnterprise(String corporatePhone);

    /**
     * 查询当前部门
     * @param username
     * @return
     */
    String queryDepartment2(String username);
}