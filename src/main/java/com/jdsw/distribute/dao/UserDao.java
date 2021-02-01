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

    Set<String> findRoleByUserName2(String username);

    UsersVo findRoleByUserName3(String username);
    /**
     * 查询部门下的人员
     * @param
     * @return
     */
    List<User> queryDepartment(Map map);

    /**
     * 查询组下的人员
     * @param
     * @return
     */
    List<User> queryGroup(Map map);

    /**
     * 查询部门主管
     * @param
     * @return
     */
    List<UsersVo> queryCharge(Map map);

    /**
     * 查询企业信息
     * @param
     * @return
     */
    List<Enterprise> queryEnterprise(String corporatePhone);

    /**
     * 查询当前部门主管名称
     * @param username
     * @return
     */
     String queryDepartment2(String username);
    /**
     * 查询当前部门主管名
     * @param
     * @return
     */
    String queryDepartment3(String name);

    /**
     * 查询自己所在的分公司名称
     * @param username
     * @return
     */
    UsersVo queryBranch(String username);

    List<String> queryGroup2(Integer leader);
}