package com.jdsw.distribute.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.dao.DevelopDao;
import com.jdsw.distribute.dao.DevelopFollowDao;
import com.jdsw.distribute.dao.NetworkDao;
import com.jdsw.distribute.dao.UserDao;
import com.jdsw.distribute.enums.Department;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.service.DevelopService;
import com.jdsw.distribute.util.Rand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DevelopServiceImpl implements DevelopService {

    @Autowired
    private DevelopDao developDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DevelopFollowDao developFollowDao;

    @Override
    public int insertDevelop(Map map) {
        String str2 =map.get("name")+"新建线索";
        Distribute distribute = (Distribute) map.get("distribute");
        Set set = userDao.findRoleByUserName2((String) map.get("username"));
        String branch = userDao.queryBranch((String) map.get("username"));
        distribute.setActivation(0);
        distribute.setLastFollowName((String) map.get("name"));
        if (StringUtils.isEmpty(distribute.getTrackId())){
            String trackId = Rand.getTrackId("ZJ");//获得跟踪单号
            distribute.setTrackId(trackId);
        }
        for (Object str : set) {
            if (str.equals(Department.CHARGE.value)) {//主管
                distribute.setBranch(branch);
            }else if (str.equals(Department.SALESMAN.value)){//业务员
                String LeaderName = userDao.queryDepartment3((String) map.get("name"));//获取主管
                distribute.setBranch(branch);
                distribute.setLeaderName(LeaderName);
            }
        }
        developDao.insertDevelop(distribute);
        DistributeFollow distributeFollow = new DistributeFollow();
        distributeFollow.setFollowResult(str2);
        distributeFollow.setNetworkId(distribute.getId());
        developFollowDao.insertDevelopFollow(distributeFollow);
        distributeFollow.setFollowResult(distribute.getLastFollowResult());
        distributeFollow.setImgUrl(distribute.getImgUrl());
        return developFollowDao.insertDevelopFollow(distributeFollow);
    }

    @Override
    public PageInfo<Distribute> developList(Map map){
        Integer pageNum = (Integer) map.get("pageNum");
        Integer limit = (Integer) map.get("limit");
        PageHelper.startPage(pageNum, limit);
        List<Distribute> Network = developDao.developList(map);
        PageInfo result = new PageInfo(Network);
        return result;
    }

    @Override
    public int updateDevelop(Distribute distribute) {
        return developDao.updateDevelop(distribute);
    }

    @Override
    public int deleteDevelop(Distribute distribute) {
        return developDao.deleteDevelop(distribute);
    }

    @Override
    public List<DistributeFollow> qureyFollowList(Integer id) {
        return developFollowDao.qureyFollowList(id);
    }

    @Override
    public int followupDevelop(DistributeFollow distributeFollow) {
        return developFollowDao.insertDevelopFollow(distributeFollow);
    }
}
