package com.jdsw.distribute.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.dao.*;
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
    @Autowired
    private CustomerDao customerDao;
    @Override
    public int insertDevelop(Map map) {
        Distribute distribute = (Distribute) map.get("distribute");
        Set set = userDao.findRoleByUserName2((String) map.get("username"));
        UsersVo usersVo = userDao.queryBranch((String) map.get("username"));
        distribute.setActivation(1);
        distribute.setLastFollowName((String) map.get("name"));
        distribute.setFirstFollowName((String) map.get("name"));
        distribute.setProposer((String) map.get("name"));
        distribute.setGrade(usersVo.getGroup());
        distribute.setSign(1);
        distribute.setFlag(0);
        distribute.setOutName((String) map.get("name"));
        distribute.setOutTime(DateUtil.getDateTime());
        distribute.setActivationTime(DateUtil.getDateTime());
        if (StringUtils.isEmpty(distribute.getReceivingTime())){
            distribute.setReceivingTime(DateUtil.getDateTime());
        }
        if (StringUtils.isEmpty(distribute.getTrackId())){
            int row = networkDao.getRowNo("XZ");
            map.put("rowCount",row+1);
            row = 202100000+row;
            StringBuffer st=new StringBuffer("XZ");
            StringBuffer trackId = st.append(row);
            distribute.setTrackId(trackId.toString());
            map.put("type","XZ");
            networkDao.updateRow(map);
        }
        if (StringUtils.isEmpty(distribute.getCustomerNo())){
            int row = networkDao.getRowNo("KH");
            map.put("rowCount",row+1);
            row = 202100000+row;
            StringBuffer st=new StringBuffer("KH");
            StringBuffer customerNo = st.append(row);
            distribute.setCustomerNo(customerNo.toString());
            map.put("type","KH");
            networkDao.updateRow(map);
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
        distributeFollow.setNetworkId(distribute.getId());
        distributeFollow.setFollowResult(distribute.getLastFollowResult());
        distributeFollow.setImgUrl(distribute.getImgUrl());
        distributeFollow.setOperation("新建线索");
        distributeFollow.setFollowName((String) map.get("name"));
        customerDao.insertCustomer(distribute);
        return developFollowDao.insertDevelopFollow(distributeFollow);
    }

    @Override
    public PageInfo<Distribute> developList(Map map){
        Integer pageNum = (Integer) map.get("pageNum");
        Integer limit = (Integer) map.get("limit");
        PageHelper.startPage(pageNum, limit);
        map.put("lastFollowName",map.get("name"));
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
        distribute.setLastFollowTime(DateUtil.getDateTime());
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
            Distribute distribute3 = new Distribute();
            Distribute distribute2 = developDao.selectDeveolpById(distribute.get(i).getId());
            distribute3.setStatus(3);
            distribute3.setTrackId(distribute2.getTrackId());
            developDao.updateBytrackId(distribute3);
            networkDao.insertDealOrder(distribute2);
            return networkDao.insertDistrbuteOrder(distribute2);
        }
        return 0;
    }
    @Override
    @Transactional
    public int transferNetwork(List<Distribute> distribute,String name) {
        DistributeFollow networkFollow;
        Distribute distribute1;
        List<Distribute> ld = new ArrayList<>();
        List<DistributeFollow> ls = new ArrayList<>();
        for (int i=0;i<distribute.size();i++){
            networkFollow = new DistributeFollow();
            distribute1 = new Distribute();
            networkFollow.setFollowName(name);
            networkFollow.setNetworkId(distribute.get(i).getId());
            networkFollow.setOperation("转交");
            ls.add(networkFollow);
            distribute1.setLastFollowName(distribute.get(0).getLeaderName());
            distribute1.setId(distribute.get(i).getId());
            distribute1.setStatus(10);
            ld.add(distribute1);
            developFollowDao.insertNetworkFollow2(ls);
        }
        return developDao.updateNetworkLastFollowName(ld);
    }
}
