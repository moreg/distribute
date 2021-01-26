package com.jdsw.distribute.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.dao.DevelopDao;
import com.jdsw.distribute.dao.NetworkDao;
import com.jdsw.distribute.dao.UserDao;
import com.jdsw.distribute.enums.Department;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.service.DevelopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DevelopServiceImpl implements DevelopService {

    @Autowired
    private DevelopDao developDao;
    @Autowired
    private UserDao userDao;
    @Override
    public List<Distribute> DevelopList(Map map) {
        return null;
    }

    @Override
    public int insertDevelop(Map map) {
        Distribute distribute = (Distribute) map.get("distribute");
        Set set = userDao.findRoleByUserName2((String) map.get("username"));
        String branch = userDao.queryBranch((String) map.get("username"));
        distribute.setActivation(0);
        for (Object str : set) {
            if (str.equals(Department.CHARGE.value)) {//主管
                distribute.setBranch(branch);

            }else if (str.equals(Department.SALESMAN.value)){//业务员
                String LeaderName = userDao.queryDepartment3((String) map.get("name"));//获取主管
                distribute.setBranch(branch);
                distribute.setLeaderName(LeaderName);
            }
        }
        return developDao.insertDevelop(distribute);
    }
}
