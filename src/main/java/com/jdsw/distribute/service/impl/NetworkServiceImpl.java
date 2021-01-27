package com.jdsw.distribute.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.dao.*;
import com.jdsw.distribute.enums.Department;
import com.jdsw.distribute.model.*;
import com.jdsw.distribute.service.NetworkService;
import com.jdsw.distribute.util.*;
import com.github.pagehelper.util.StringUtil;

import com.jdsw.distribute.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.jdsw.distribute.util.DateUtil.parseDate;


@Service
public class NetworkServiceImpl implements NetworkService {
    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    TransactionDefinition transactionDefinition;
    @Autowired
    private NetworkDao networkDao;
    @Autowired
    private NetworkFollowDao networkFollowDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TelemarkeDao telemarkeDao;
    @Autowired
    private TelemarkeFollowDao telemarkeFollowDao;
    @Autowired
    private EnterpriseDao enterpriseDao;
    @Override
    @Transactional
    public int appoint(List<Distribute> network, String name) {
        Distribute distribute;
        DistributeFollow networkFollow;
        String leader = userDao.queryDepartment3(network.get(0).getFirstFollowName());
        String str = "给"+network.get(0).getFirstFollowName()+"转交了一条线索";
        List<DistributeFollow> ls = new ArrayList<>();
        List<Distribute> ld = new ArrayList<>();
        for (int i=0;i<network.size();i++){
            distribute = new Distribute();
            networkFollow = new DistributeFollow();
            distribute.setId(network.get(i).getId());
            distribute.setDepartment(network.get(i).getDepartment());
            distribute.setAppoint(0);
            distribute.setBranch(network.get(i).getBranch());
            distribute.setLeaderName(leader);
            distribute.setStatus(0);
            if (StringUtil.isNotEmpty(network.get(i).getFirstFollowName())){
                distribute.setAppoint(1);
                distribute.setLeaderSign(0);
                distribute.setStatus(10);
                distribute.setFirstFollowName(network.get(i).getFirstFollowName());
                distribute.setLastFollowName(network.get(i).getFirstFollowName());
                distribute.setOverdueTime(DateUtil.getOverTime(600000));
                networkFollow.setFollowName(name);
                networkFollow.setNetworkId(network.get(i).getId());
                networkFollow.setFollowResult(str);
                ls.add(networkFollow);
            }
            ld.add(distribute);
        }
        if(ls.size() > 0){
            networkFollowDao.insertNetworkFollow2(ls);
        }
        return networkDao.appoint(ld);
    }
    @Override
    @Transactional
    public int orderTaking(Distribute network,String username,String name) {
        TransactionStatus transactionStatus = null;
        int i = 0;
        try {
            String str = name+"领取了线索";
            DistributeFollow networkFollow = new DistributeFollow();
            networkFollow.setFollowName(name);
            networkFollow.setNetworkId(network.getId());
            networkFollow.setFollowResult(str);
            networkFollowDao.insertNetworkFollow(networkFollow);
            String leader = userDao.queryDepartment2(username);
            network.setLeaderName(leader);
            network.setStatus(10);
            i = networkDao.updateNetworkFirstFollowName(network);
            transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
            networkDao.selectNetworkById2(network.getId());
        }catch (Exception e){
            //dataSourceTransactionManager.rollback(transactionStatus);
        }finally {
            dataSourceTransactionManager.commit(transactionStatus);
        }
        return i;
    }

    @Override
    public PageInfo<Distribute> queryNetworkByLastName(int pageNum, int limit,String content, String strtime, String endtime,String lastFollowName,String username) throws ParseException {
        Set set = userDao.findRoleByUserName2(username);
        for (Object str : set) {
            if (str.equals(Department.CHARGE.value)) {//主管
                PageHelper.startPage(pageNum, limit);
                List<Distribute> Network = networkDao.queryNetworkByLastName(content,strtime,endtime,lastFollowName);
                PageInfo result = new PageInfo(Network);
                return result;
            }else if (str.equals(Department.SALESMAN.value)){//业务员
                PageHelper.startPage(pageNum, limit);
                List<Distribute> Network = networkDao.queryNetworkByLastName2(content,strtime,endtime,lastFollowName);
                PageInfo result = new PageInfo(Network);
                return result;
            }
        }
        return null;
    }

    @Override
    public PageInfo<Distribute> pendingNetworkList(int pageNum, int limit, String content, String strtime, String endtime, String lastFollowName) throws ParseException  {
        PageHelper.startPage(pageNum, limit);
        List<Distribute> Network = networkDao.pendingNetworkList(content,strtime,endtime,lastFollowName);
        PageInfo result = new PageInfo(Network);
        return result;
    }


    @Override
    public PageInfo<Distribute> airForcePoolList(Map map) {
        Set set = userDao.findRoleByUserName2((String) map.get("username"));
        Distribute distribute = (Distribute) map.get("distribute");
        Integer pageNum = (Integer) map.get("pageNum");
        Integer limit = (Integer) map.get("limit");
        map.put("issue",distribute.getIssue());
        for (Object str : set) {
            if (str.equals(Department.AirCUSTOMER.value)){//线索管理员
                PageHelper.startPage(pageNum,limit);
                List<Distribute> Network = networkDao.airForcePoolList(map);
                PageInfo result = new PageInfo(Network);
                return result;
            }
        }
        return null;
    }

    @Override
    public PageInfo<Distribute> grabbingPool(Map map) {
        List<Distribute> Network = networkDao.grabbingPool(map);
        PageInfo result = new PageInfo(Network);
        return result;
    }

    @Override
    public PageInfo<Distribute> withPool(Map map) {
        map.put("lastFollowName",map.get("name"));
        List<Distribute> Network = networkDao.withPool(map);
        PageInfo result = new PageInfo(Network);
        return result;
    }



    @Override
    public PageInfo<Distribute> pendingPoolList(Map map) {
        Set set = userDao.findRoleByUserName2((String) map.get("username"));
        Integer pageNum = (Integer) map.get("pageNum");
        Integer limit = (Integer) map.get("limit");
        List<Distribute> Network = null;
        for (Object str : set) {
            if (str.equals(Department.AirCUSTOMER.value)){
                PageHelper.startPage(pageNum, limit);
                Network = networkDao.pendingPoolList(map);
            }
        }
        PageInfo result = new PageInfo(Network);
        return result;
    }


    @Override
    public int insertNetwoork(Map map) {
        DistributeFollow distributeFollow = new DistributeFollow();
        String str2 = map.get("name")+"新建线索";
        Set set = userDao.findRoleByUserName2((String) map.get("username"));//获取角色
        Distribute distribute = (Distribute) map.get("distribute");
        distributeFollow.setFollowResult(str2);
        distributeFollow.setFollowName((String) map.get("name"));
        distribute.setActivation(0);
        distribute.setAppoint(0);
        distribute.setSign(1);
        distribute.setInvalid(0);
        if (StringUtils.isEmpty(distribute.getTrackId())){
            String trackId = Rand.getTrackId("KZ");//获得跟踪单号
            distribute.setTrackId(trackId);
        }
        for (Object str : set) {//遍历角色
            if (str.equals(Department.SALESMAN.value)){//业务员
                String LeaderName = userDao.queryDepartment2((String) map.get("username"));//获取主管
                distribute.setLastFollowName((String) map.get("name"));
                distribute.setFirstFollowName((String) map.get("name"));
                distribute.setIssue(1);
                distribute.setStatus(10);
                distribute.setLeaderSign(0);
                distribute.setAppoint(1);
                distribute.setLeaderName(LeaderName);
            } else if (str.equals(Department.CHARGE.value)){//主管
                distribute.setLeaderName((String) map.get("name"));
                distribute.setIssue(0);
                distribute.setLeaderSign(1);
                distribute.setStatus(5);
            }else {//客服
                String LeaderName = userDao.queryDepartment3((distribute.getLastFollowName()));//获取主管
                if (StringUtils.isNotEmpty(distribute.getLastFollowName())){
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                    distribute.setReceivingTime(df.format(new Date()));
                    distribute.setStatus(10);
                    distribute.setIssue(1);
                    String LeaderName2 = userDao.queryDepartment3(distribute.getLastFollowName());//获取主管
                    distribute.setLeaderName(LeaderName2);
                }else if (StringUtils.isNotEmpty(distribute.getBranch())){
                    distribute.setIssue(1);
                    distribute.setStatus(0);
                }else {
                    distribute.setIssue(0);
                    distribute.setStatus(0);
                }
                distribute.setLeaderSign(1);
                distribute.setProposer((String) map.get("name"));
            }
        }
        networkDao.insertNetwoork(distribute);
        distributeFollow.setNetworkId(distribute.getId());
        networkFollowDao.insertNetworkFollow(distributeFollow);
        distributeFollow.setFollowResult(distribute.getLastFollowResult());
        distributeFollow.setImgUrl(distribute.getImgUrl());
        return networkFollowDao.insertNetworkFollow(distributeFollow);
    }

    @Override
    public int excelNetwork(MultipartFile file,String username,String name) throws Exception {
        String filePath = "E:\\upexl\\";
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String newFileName = file.getOriginalFilename();
        File newFile = new File(filePath + newFileName);
        //复制操作
        file.transferTo(newFile);
        List<Object> result = excelRead.ReadExcelByPOJO(newFile.toString(),2,9, Excel.class);
        List ls = new ArrayList();
        Set set = userDao.findRoleByUserName2(username);
        String LeaderName = userDao.queryDepartment2(username);//获取主管
        for (Object str : set) {
            if (str.equals(Department.SALESMAN.value)){//业务员
                for (int i = 0;i<result.size();i++){
                    Object ob = result.get(i);
                    Map map = JSON.parseObject(JSON.toJSONString(ob),Map.class);
                    String trackId = Rand.getTrackId("KZ");//获得跟踪单号
                    map.put("trackId",trackId);
                    map.put("lastFollowName",name);
                    map.put("firstFollowName",name);
                    map.put("issue",1);
                    map.put("leaderSign",0);
                    map.put("appoint",1);
                    map.put("status",10);
                    map.put("leaderName",LeaderName);
                    ls.add(map);
                }
            }if (str.equals(Department.CHARGE.value)){//主管
                for (int i = 0;i<result.size();i++){
                    Object ob = result.get(i);
                    Map map = JSON.parseObject(JSON.toJSONString(ob),Map.class);
                    String trackId = Rand.getTrackId("KZ");//获得跟踪单号
                    map.put("trackId",trackId);
                    map.put("leaderName",name);
                    map.put("lastFollowName","");
                    map.put("firstFollowName","");
                    map.put("issue",0);
                    map.put("appoint",0);
                    map.put("leaderSign",1);
                    map.put("status",5);
                    ls.add(map);
                }
            }else {
                for (int i = 0;i<result.size();i++){//客服导入
                    Object ob = result.get(i);
                    Map map = JSON.parseObject(JSON.toJSONString(ob),Map.class);
                    String trackId = Rand.getTrackId("KZ");//获得跟踪单号
                    map.put("trackId",trackId);
                    map.put("issue",0);
                    map.put("status",0);
                    map.put("lastFollowName","");
                    map.put("firstFollowName","");
                    map.put("leaderSign",1);
                    ls.add(map);
                }
            }

        }
        return networkDao.excelNetwork(ls);
    }

    @Override
    public int deleteNetwork(Distribute distribute) {
        return networkDao.deleteNetwork(distribute);
    }

    @Override
    public int updateNetwork(Distribute distribute) {
        return networkDao.updateNetwork(distribute);
    }

    @Override
    public int overTime(Distribute network) {
        network.setStatus(3);
        network.setLastFollowName("");
        return networkDao.overTime(network);
    }

    @Override
    @Transactional
    public int followupNetwork(DistributeFollow networkFollow,String username) {
        Distribute distribute = new Distribute();
        distribute.setLastFollowResult(networkFollow.getFollowResult());
        distribute.setTrackId(networkFollow.getTrackId());
        distribute.setOverdueTime(networkFollow.getFollowTime());
        distribute.setLastFollowTime(networkFollow.getFollowTime());
        networkDao.updateNetwork2(distribute);
        return networkFollowDao.insertNetworkFollow(networkFollow);
    }

    @Override
    @Transactional
    public int submitRecordingNetwork(List<Distribute> distribute) {
        for (int i=0;i<distribute.size();i++){
            Distribute distribute2;
            distribute.get(i).setStatus(3);
            distribute2 = networkDao.selectNetworkById(distribute.get(i).getId());
            networkDao.insertDealOrder(distribute2);
        }
        return networkDao.SubmitRecordingNetwork2(distribute);
    }

    @Override
    public int updateRecordingNetwork(Map map) {
        Distribute distribute = (Distribute) map.get("distribute");
        distribute.setStatus(4);
        String tid = networkDao.qureydealOrder(distribute);
        distribute.setTrackId(tid);
        int i = networkDao.updateBytrackId(distribute);
        telemarkeDao.updateBytrackId(distribute);
        distribute = networkDao.selectNetworkById3(tid);
        Enterprise enterprise = new Enterprise();
        enterprise.setAddName((String) map.get("name"));
        enterprise.setCorporateName(distribute.getName());
        enterprise.setCorporatePhone(distribute.getCorporatePhone());
        enterprise.setCorporatePhone2(distribute.getCorporatePhone2());
        enterprise.setCorporatePhone3(distribute.getCorporatePhone3());
        enterprise.setTrackId(tid);
        enterprise.setSource(distribute.getSource());
        enterpriseDao.insertEnterprise(enterprise);
        return networkDao.UpdateRecordingNetwork(distribute);
    }

    @Override
    public PageInfo<CashierVo> cashierCompleteLis(Map map) {
        Set set = userDao.findRoleByUserName2((String) map.get("username"));
        Integer pageNum = (Integer) map.get("pageNum");
        Integer limit = (Integer) map.get("limit");
        map.put("orderState",1);
        for (Object str : set) {
            if (str.equals(Department.FINANCE.value) ) {//财务
                map.put("name",null);
                PageHelper.startPage(pageNum, limit);
                List<CashierVo> CashierVo = networkDao.cashierListNetwork(map);
                PageInfo result = new PageInfo(CashierVo);
                return result;
            }
            PageHelper.startPage(pageNum, limit);
            List<CashierVo> CashierVo = networkDao.cashierListNetwork(map);
            PageInfo result = new PageInfo(CashierVo);
            return result;
        }
        return null;
    }

    @Override
    public PageInfo<CashierVo> cashierListNetwork(Map map) {
        Set set = userDao.findRoleByUserName2((String) map.get("username"));
        Integer pageNum = (Integer) map.get("pageNum");
        Integer limit = (Integer) map.get("limit");
        map.put("name",null);
        map.put("orderState",0);
        for (Object str : set) {
            if (str.equals(Department.FINANCE.value)) {
                PageHelper.startPage(pageNum, limit);
                List<CashierVo> CashierVo = networkDao.cashierListNetwork(map);
                PageInfo result = new PageInfo(CashierVo);
                return result;
            }
        }
       return null;
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
            distribute1.setFirstFollowName(distribute.get(0).getLeaderName());
            distribute1.setOverdueTime(DateUtil.getOverTime(600000));
            distribute1.setId(distribute.get(i).getId());
            distribute1.setStatus(10);
            distribute1.setLeaderSign(1);
            ld.add(distribute1);
            networkFollowDao.insertNetworkFollow2(ls);
        }
        return networkDao.updateNetworkLastFollowName(ld);
    }

    @Override
    @Transactional
    public int customerTransfer(List<Distribute> distribute,String name) {
        String str = "给"+distribute.get(0).getLeaderName()+"转交了一条线索";
        DistributeFollow networkFollow;
        Distribute distribute1;
        List<DistributeFollow> ls = new ArrayList<>();
        List<Distribute> ld = new ArrayList<>();
        for (int i=0;i<distribute.size();i++){
            distribute1 = new Distribute();
            networkFollow = new DistributeFollow();
            networkFollow.setFollowName(name);
            networkFollow.setNetworkId(distribute.get(i).getId());
            networkFollow.setFollowResult(str);
            ls.add(networkFollow);
            distribute1.setLastFollowName(distribute.get(i).getLastFollowName());
            distribute1.setLeaderName(distribute.get(i).getLeaderName());
            distribute1.setId(distribute.get(i).getId());
            distribute1.setStatus(5);
            networkFollowDao.insertNetworkFollow2(ls);
            ld.add(distribute1);
        }
        return networkDao.updateNetworkLastFollowName(ld);
    }

    @Override
    public int setOverdueTime(Distribute distribute) {
        distribute.setOverdueTime(DateUtil.getOverTime(distribute.getMsec()));
        return networkDao.setOverdueTime(distribute);
    }

    @Override
    public List<DistributeFollow> qureyFollowList(Integer id,String trackId) {
        String str = trackId.substring(0,2);
        if ("KZ".equals(str)){
            return networkFollowDao.qureyFollowList(id);
        }else if ("LJ".equals(str)){
            return telemarkeFollowDao.qureyFollowList(id);
        }
        return null;
    }

    @Override
    public Distribute qureyCustomer(Integer id, String trackId) {
        String str = trackId.substring(0,2);
        if ("KZ".equals(str)){
            return networkDao.selectNetworkById(id);
        }else if ("LJ".equals(str)){
            return telemarkeDao.selectNetworkById(id);
        }
        return null;
    }

    @Override
    public List enterpriseList(Map map) {
        return enterpriseDao.enterpriseList(map);
    }

    @Override
    public List business(Map map) {
        return null;
    }

    @Override
    @Transactional
    public int returnPool(Map map) {
        Distribute distribute = (Distribute) map.get("distribute");
        DistributeFollow networkFollow;
        String str = map.get("name")+"提交退单";
        String trid = distribute.getTrackId().substring(0, 2);;
        networkFollow = new DistributeFollow();
        networkFollow.setFollowName((String) map.get("name"));
        networkFollow.setNetworkId(distribute.getId());
        networkFollow.setFollowResult(str);
        if ("KZ".equals(trid)){
            networkFollowDao.insertNetworkFollow(networkFollow);
            networkFollow.setImgUrl(distribute.getImgUrl());
            networkFollow.setFollowResult(distribute.getLastFollowResult());
            networkFollowDao.insertNetworkFollow(networkFollow);
        }
        distribute.setIssue(3);
        distribute.setStatus(7);
        return networkDao.updateBytrackId(distribute);
    }

    @Override
    @Transactional
    public int chargeback(Map map) {
        Distribute distribute = (Distribute) map.get("distribute");
        Distribute distribute2 = networkDao.selectNetworkById3(distribute.getTrackId());
        DistributeFollow networkFollow = new DistributeFollow();
        networkFollow.setFollowName((String) map.get("name"));
        networkFollow.setNetworkId(distribute.getId());
        String str = map.get("name")+"驳回申请";
        if (distribute2.getInvalid() == 1){
            distribute.setStatus(5);
        }else {
            distribute.setStatus(10);
        }
        networkFollow.setFollowResult(str);
        networkFollowDao.insertNetworkFollow(networkFollow);
        networkFollow.setImgUrl(distribute.getImgUrl());
        networkFollow.setFollowResult(distribute.getLastFollowResult());
        networkFollowDao.insertNetworkFollow(networkFollow);
        distribute.setIssue(1);
        distribute.setInvalid(1);
        distribute.setOverdueTime(DateUtil.getOverTime(86400000));
        return networkDao.updateBytrackId(distribute);
    }

    @Override
    @Transactional
    public int adopt(Map map) {
        Distribute distribute = (Distribute) map.get("distribute");
        String str = map.get("name")+"通过申请";
        DistributeFollow networkFollow = new DistributeFollow();
        networkFollow.setFollowName((String) map.get("name"));
        networkFollow.setNetworkId(distribute.getId());
        networkFollow.setFollowResult(str);
        networkFollowDao.insertNetworkFollow(networkFollow);
        networkFollow.setImgUrl(distribute.getImgUrl());
        networkFollow.setFollowResult(distribute.getLastFollowResult());
        networkFollowDao.insertNetworkFollow(networkFollow);
        distribute.setLastFollowName(null);
        distribute.setReceivingTime(null);
        distribute.setIssue(0);
        distribute.setStatus(1);
        distribute.setSign(0);
        networkDao.updateBytrackId(distribute);
        return networkDao.updateBytrackId2(distribute);
    }

    @Override
    public PageInfo<Distribute> statusList(int pageNum, int limit, Integer status,String name) {
        Distribute distribute1 = new Distribute();
        distribute1.setLastFollowName(name);
        distribute1.setStatus(status);
        PageHelper.startPage(pageNum, limit);
        List<Distribute> distribute = networkDao.statusList(distribute1);
        PageInfo result = new PageInfo(distribute);
        return result;
    }

    @Override
    public int setOvertime(Distribute distribute) {
        return networkDao.setOvertime(distribute);
    }

}
