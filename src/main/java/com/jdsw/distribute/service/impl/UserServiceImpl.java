package com.jdsw.distribute.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.Enterprise;
import com.jdsw.distribute.model.Role;
import com.jdsw.distribute.service.UserService;
import com.jdsw.distribute.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.dao.UserDao;
import com.jdsw.distribute.model.User;
import com.jdsw.distribute.util.MD5Utils;
import com.jdsw.distribute.vo.UsersVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "userService")
@SuppressWarnings("unchecked")
public class UserServiceImpl implements UserService {
    @Autowired

    private UserDao userDao;

    @Override
    public PageInfo<UsersVo> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<UsersVo> userDomains = userDao.selectUsers();
        PageInfo result = new PageInfo(userDomains);
        return result;
    }

    @Override
    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public Set<String> findPermissionByUserName(String username) {
        return userDao.findPermissionByUserName(username);
    }

    @Override
    public int countUser() {
        return userDao.countUser();
    }

    @Override
    public int insertUser(User user) {
         User user2 = userDao.findByUserName(user.getUsername());
        if (user2 != null){
            return 3;
        }
        String paw = user.getPassword();
        String rand = MD5Utils.getRandomString(24);
        String salt = MD5Utils.loginMd5(paw,rand);
        String date = DateUtil.getDate();
        user.setPassword(salt);
        user.setSalt(rand);
        user.setState(0);
        user.setAddtime(date);
        return userDao.insert(user);
    }

    @Override
    public Set<String> findRoleByUserName(String username) {
        return userDao.findRoleByUserName(username);
    }

    @Override
    public List<User> queryDepartment(String department) {
        String temp[]=department.split(",");
        List<String> list = new ArrayList();
        for (int i=0;i<temp.length;i++){
            String string1 = temp[i];
            list.add(temp[i]);
        }
      /*  Map map2 = new HashMap();
        map2.put("list",list);
        map2.put("branch",branch);*/
        List<User> ls = userDao.queryDepartment(list);
        List li = new ArrayList<>();
        for(int i=0;i<ls.size();i++){
            Map<String,Object> map = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(ls.get(i).getName())){
                map.put("name",ls.get(i).getName());
                map.put("department",ls.get(i).getDepartment());
                li.add( map);
            }
        }
        return li;
    }

    @Override
    public List<UsersVo> queryCharge(String department) {
        String temp[]=department.split(",");
        List<String> list = new ArrayList();
        for (int i=0;i<temp.length;i++){
            String string1 = temp[i];
            list.add(temp[i]);
        }
/*        Map map2 = new HashMap();
        map2.put("list",list);
        map2.put("branch",branch);*/
        List<UsersVo> ls = userDao.queryCharge(list);
        List li = new ArrayList();
        for(int i=0;i<ls.size();i++){
            Map map = new HashMap<>();
            if (StringUtil.isNotEmpty(ls.get(i).getName())){
                map.put("name",ls.get(i).getName());
                map.put("department",ls.get(i).getDepartment());
                li.add(map);
            }
        }
        return li;
    }

    @Override
    public List<Enterprise> queryEnterprise(String corporatePhone) {
        return userDao.queryEnterprise(corporatePhone);
    }
}
