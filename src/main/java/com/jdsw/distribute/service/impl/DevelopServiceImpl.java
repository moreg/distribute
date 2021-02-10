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
import com.jdsw.distribute.util.DateUtil;
import com.jdsw.distribute.util.Rand;
import com.jdsw.distribute.vo.InsertVo;
import com.jdsw.distribute.vo.UsersVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
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
    @Autowired
    private NetworkDao networkDao;

    @Override
    public int insertDevelop(Map map) {
        String str2 =map.get("name")+"新建线索";
        Distribute distribute = (Distribute) map.get("distribute");
        Set set = userDao.findRoleByUserName2((String) map.get("username"));
        UsersVo usersVo = userDao.queryBranch((String) map.get("username"));
        distribute.setActivation(1);
        distribute.setLastFollowName((String) map.get("name"));
        distribute.setGrade(usersVo.getGroup());
        if (StringUtils.isEmpty(distribute.getTrackId())){
            String trackId = Rand.getTrackId("Z");//获得跟踪单号
            distribute.setTrackId(trackId);
        }
        for (Object str : set) {
            if (str.equals(Department.CHARGE.value)) {//主管
                distribute.setBranch(usersVo.getBranch());
            }else if (str.equals(Department.SALESMAN.value)){//业务员
                String LeaderName = userDao.queryDepartment3((String) map.get("name"));//获取主管
                distribute.setBranch(usersVo.getBranch());
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
    public Distribute updateDevelopPop(Integer id) {
        return developDao.selectDeveolpById(id);
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
    @Transactional
    public int followupDevelop(DistributeFollow distributeFollow) {
        Distribute distribute = new Distribute();
        distribute.setId(distributeFollow.getNetworkId());
        distribute.setLastFollowResult(distributeFollow.getFollowResult());
        distribute.setLastFollowTime(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
        developDao.updateDevelop(distribute);
        return developFollowDao.insertDevelopFollow(distributeFollow);
    }
    @Override
    public Distribute selectDeveolpById(Integer id) {
        return developDao.selectDeveolpById(id);
    }

    @Override
    @Transactional
    public int submitRecordingNetwork(List<Distribute> distribute) {
        for (int i=0;i<distribute.size();i++){
            Distribute distribute2 = new Distribute();
            Distribute distribute3 = new Distribute();
            distribute2 = developDao.selectDeveolpById(distribute.get(i).getId());
            distribute3.setStatus(3);
            distribute3.setTrackId(distribute2.getTrackId());
            developDao.updateBytrackId(distribute3);
            System.out.println(distribute2);
            networkDao.insertDealOrder(distribute2);
            return networkDao.insertDistrbuteOrder(distribute2);
        }
        return 0;
    }
    @Override
    @Transactional
    public int transferNetwork(List<Distribute> distribute,String name) {
        String str = "给"+distribute.get(0).getLeaderName()+"转交了一条线索";
        DistributeFollow networkFollow;
        Distribute distribute1;
        List<Distribute> ld = new ArrayList<>();
        List<DistributeFollow> ls = new ArrayList<>();
        for (int i=0;i<distribute.size();i++){
            networkFollow = new DistributeFollow();
            distribute1 = new Distribute();
            networkFollow.setFollowName(name);
            networkFollow.setNetworkId(distribute.get(i).getId());
            networkFollow.setFollowResult(str);
            ls.add(networkFollow);
            distribute1.setLastFollowName(distribute.get(0).getLeaderName());
            distribute1.setId(distribute.get(i).getId());
            distribute1.setStatus(10);
            ld.add(distribute1);
            System.out.println(ls);
            developFollowDao.insertNetworkFollow2(ls);
        }
        return developDao.updateNetworkLastFollowName(ld);
    }
}
