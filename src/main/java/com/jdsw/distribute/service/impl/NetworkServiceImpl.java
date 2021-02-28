package com.jdsw.distribute.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.dao.*;
import com.jdsw.distribute.dao.DealOrderDao;
import com.jdsw.distribute.enums.Department;
import com.jdsw.distribute.model.*;
import com.jdsw.distribute.service.NetworkService;
import com.jdsw.distribute.util.*;
import com.github.pagehelper.util.StringUtil;

import com.jdsw.distribute.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.jdsw.distribute.util.DateUtil.parseDate;


@Service
public class NetworkServiceImpl implements NetworkService {
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
    @Autowired
    private DealOrderDao dealOrderDao;
    @Autowired
    private DevelopDao developDao;
    @Autowired
    private DevelopFollowDao developFollowDao;
    @Autowired
    private BusinessDao businessDao;
    @Autowired
    private CustomerOrderDao customerOrderDao;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    @Transactional
    public int appoint(List<Distribute> network, String name) {
        Distribute distribute;
        DistributeFollow networkFollow;
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
            distribute.setStatus(0);
            //指定人
            if (StringUtil.isNotEmpty(network.get(i).getFirstFollowName())){
                String role = userDao.findRoleByUserName4(network.get(0).getFirstFollowName());
                if (!Department.CHARGE.value.equals(role)){
                    String leader = userDao.queryDepartment3(network.get(0).getFirstFollowName());
                    distribute.setLeaderName(leader);
                }
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
    public int orderTaking(Distribute network,String username,String name,String role) {
        int i = 0;
        try {
            UsersVo usersVo = userDao.queryBranch(username);
            String str = name+"领取了线索";
            DistributeFollow networkFollow = new DistributeFollow();
            networkFollow.setFollowName(name);
            networkFollow.setNetworkId(network.getId());
            networkFollow.setFollowResult(str);
            networkFollowDao.insertNetworkFollow(networkFollow);
            if (!Department.CHARGE.value.equals(role)){
                String leader = userDao.queryDepartment2(username);
                network.setLeaderName(leader);
            }
            network.setStatus(10);
            network.setBranch(usersVo.getBranch());
            i = networkDao.updateNetworkFirstFollowName(network);
        }catch (Exception e){
        }finally {

        }
        return i;
    }

    @Override
    public PageInfo<Distribute> queryNetworkByLastName(Map map) throws ParseException {
        Set set = userDao.findRoleByUserName2((String) map.get("username"));
        Integer pageNum = (Integer) map.get("pageNum");
        Integer limit = (Integer) map.get("limit");
        for (Object str : set){
            if (str.equals(Department.ADMIN.value) || str.equals(Department.GENERAL.value) || str.equals(Department.DEPUTY.value)){
                map.put("lastFollowName",null);
            }
        }
        map.put("status",10);
        PageHelper.startPage(pageNum, limit);
        List<Distribute> Network = networkDao.queryNetworkByLastName(map);
        PageInfo result = new PageInfo(Network);
        return result;

    }

    @Override
    public PageInfo<Enterprise> enterprisePoolList(Map map) {
        Integer pageNum = (Integer) map.get("pageNum");
        Integer limit = (Integer) map.get("limit");
        Set set = userDao.findRoleByUserName2((String) map.get("username"));
        for (Object str : set){
            if (str.equals(Department.ADMIN.value) || str.equals(Department.GENERAL.value) || str.equals(Department.DEPUTY.value)){
                map.put("name",null);
            }
        }
        PageHelper.startPage(pageNum, limit);
        List<Enterprise> enterprises = enterpriseDao.enterprisePoolList(map);
        PageInfo result = new PageInfo(enterprises);
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
                map.put("proposer",map.get("name"));
                PageHelper.startPage(pageNum,limit);
                List<Distribute> Network = networkDao.airForcePoolList(map);
                PageInfo result = new PageInfo(Network);
                return result;
            }else if (str.equals(Department.ADMIN.value)){//超级管理员
                PageHelper.startPage(pageNum,limit);
                List<Distribute> Network = networkDao.airForcePoolList(map);
                PageInfo result = new PageInfo(Network);
                return result;
            }else if (str.equals(Department.AIRCHARGE.value) || str.equals(Department.CLUECHARGE.value)){//空军线索主管
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
        Set set = userDao.findRoleByUserName2((String) map.get("username"));
        for (Object str : set){
            if (str.equals(Department.ADMIN.value)){
                List<Distribute> Network = networkDao.grabbingPool(map);
                PageInfo result = new PageInfo(Network);
                return result;
            }
        }
        UsersVo usersVo = userDao.queryBranch((String) map.get("username"));
        map.put("branch",usersVo.getBranch());
        List<Distribute> Network = networkDao.grabbingPool(map);
        PageInfo result = new PageInfo(Network);
        return result;
    }

    @Override
    public PageInfo<Distribute> withPool(Map map) {
        Set set = userDao.findRoleByUserName2((String) map.get("username"));
        map.put("lastFollowName",map.get("name"));
        String pool = (String) map.get("pool");
        for (Object str : set) {
            if (str.equals(Department.CHARGE.value)){
                if ("K".equals(pool)){
                    List<Distribute> Network = networkDao.withPool2(map);
                    PageInfo result = new PageInfo(Network);
                    return result;
                }else if ("L".equals(pool)){
                    List<Distribute> Network = telemarkeDao.withPool2(map);
                    PageInfo result = new PageInfo(Network);
                    return result;
                }

            }
            if (str.equals(Department.SALESMAN.value)){
                if ("K".equals(pool)){
                    List<Distribute> Network = networkDao.withPool(map);
                    PageInfo result = new PageInfo(Network);
                    return result;
                }else if ("L".equals(pool)){
                    List<Distribute> Network = telemarkeDao.withPool(map);
                    PageInfo result = new PageInfo(Network);
                    return result;
                }

            }
            if (str.equals(Department.ADMIN.value)){
                map.put("lastFollowName",null);
                if ("K".equals(pool)){
                    List<Distribute> Network = networkDao.withPool(map);
                    PageInfo result = new PageInfo(Network);
                    return result;
                }else if ("L".equals(pool)){
                    List<Distribute> Network = telemarkeDao.withPool(map);
                    PageInfo result = new PageInfo(Network);
                    return result;
                }
            }
        }
        return null;
    }
    @Override
    public Distribute insertNetwoork(Map map) {
        long num =redisUtil.incr("networkcount",1);
        num = 202100000+num;
        StringBuffer st=new StringBuffer("XK");
        StringBuffer trackId = st.append(num);
        DistributeFollow distributeFollow = new DistributeFollow();
        String str2 = map.get("name")+"新建线索";
        Set set = userDao.findRoleByUserName2((String) map.get("username"));//获取角色
        Distribute distribute = (Distribute) map.get("distribute");
        distributeFollow.setFollowResult(str2);
        distributeFollow.setFollowName((String) map.get("name"));
        distribute.setActivation(0);
        distribute.setAppoint(0);
        distribute.setSign(1);
        distribute.setIssue(0);
        distribute.setInvalid(0);
        distribute.setOverrun(0);
        distribute.setTrackId(trackId.toString());
       /* if (StringUtils.isEmpty(distribute.getTrackId())){
            String trackId = Rand.getTrackId("K");//获得跟踪单号
            distribute.setTrackId(trackId);
        }*/
        for (Object str : set) {//遍历角色
            if (str.equals(Department.AirCUSTOMER.value) || str.equals(Department.AIRCHARGE.value) ||str.equals(Department.ADMIN.value)){//客服
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
        networkFollowDao.insertNetworkFollow(distributeFollow);
        return distribute;
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
        List<Object> result = excelRead.ReadExcelByPOJO(newFile.toString(),2,-1, Excel.class);
        List ls = new ArrayList();
        Set set = userDao.findRoleByUserName2(username);
        for (Object str : set) {
            if (str.equals(Department.SALESMAN.value)){//业务员
                UsersVo usersVo = userDao.queryBranch(username);
                String LeaderName = userDao.queryDepartment2(username);//获取主管
                for (int i = 0;i<result.size();i++){
                    Object ob = result.get(i);
                    Map map = JSON.parseObject(JSON.toJSONString(ob),Map.class);
                    String trackId = Rand.getTrackId("Z");//获得跟踪单号
                    map.put("trackId",trackId);
                    map.put("lastFollowName",name);
                    map.put("activation",1);
                    map.put("branch",usersVo.getBranch());
                    map.put("leaderName",LeaderName);
                    map.put("group",usersVo.getGroup());
                    map.put("activationName",name);
                    map.put("department",usersVo.getDepartment());
                    ls.add(map);

                }
                return developDao.excelNetwork(ls);
            }
            if (str.equals(Department.CHARGE.value)){//主管
                UsersVo usersVo = userDao.queryBranch(username);
                for (int i = 0;i<result.size();i++){
                    Object ob = result.get(i);
                    Map map = JSON.parseObject(JSON.toJSONString(ob),Map.class);
                    String trackId = Rand.getTrackId("Z");//获得跟踪单号
                    map.put("trackId",trackId);
                    map.put("lastFollowName",name);
                    map.put("leaderName",null);
                    map.put("activation",1);
                    map.put("group",usersVo.getGroup());
                    map.put("branch",usersVo.getBranch());
                    map.put("lastFollowName",name);
                    map.put("activationName",name);
                    ls.add(map);
                }
                return networkDao.excelNetwork(ls);
            }
            if (str.equals(Department.AirCUSTOMER.value) || str.equals(Department.AIRCHARGE.value)){
                for (int i = 0;i<result.size();i++){//客服导入
                    Object ob = result.get(i);
                    Map map = JSON.parseObject(JSON.toJSONString(ob),Map.class);
                    long num =redisUtil.incr("networkcount",1);
                    num = 202100000+num;
                    StringBuffer st=new StringBuffer("XK");
                    StringBuffer trackId = st.append(num);
                    //String trackId = Rand.getTrackId("K");//获得跟踪单号
                    map.put("trackId",trackId);
                    map.put("issue",0);
                    map.put("status",0);
                    map.put("leaderSign",1);
                    map.put("proposer",name);
                    map.put("sign",1);
                    map.put("activation",0);
                    map.put("appoint",0);
                    map.put("invalid",0);
                    map.put("overrun",0);
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
    public Distribute updateNetworkPop(Integer id) {
        return networkDao.selectNetworkById(id);
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
        distribute.setNextTime(networkFollow.getFollowTime());
        distribute.setLastFollowTime(networkFollow.getFollowTime());
        networkDao.updateNetwork2(distribute);
        return networkFollowDao.insertNetworkFollow(networkFollow);
    }

    @Override
    @Transactional
    public int submitRecordingNetwork(List<Distribute> distribute) {
        for (int i=0;i<distribute.size();i++){
            Distribute distribute2  = new Distribute();
            distribute.get(i).setStatus(3);
            distribute2 = networkDao.selectNetworkById(distribute.get(i).getId());
            networkDao.insertDealOrder(distribute2);
            networkDao.insertDistrbuteOrder(distribute2);
        }
        return networkDao.SubmitRecordingNetwork2(distribute);
    }

    @Override
    @Transactional
    public int updateRecordingNetwork(Map map) {
        Distribute distribute = (Distribute) map.get("distribute");
        distribute.setStatus(4);
        distribute.setIssue(4);
        String tid = networkDao.qureydealOrder(distribute);
        distribute.setTrackId(tid);
        if ("K".equals(tid.substring(0,1))){
            networkDao.updateBytrackId(distribute);
            customerOrderDao.updateBytrackId(distribute);
            distribute = networkDao.selectNetworkById3(tid);
        }else if ("L".equals(tid.substring(0,1))){
            telemarkeDao.updateBytrackId(distribute);
            customerOrderDao.updateBytrackId(distribute);
            distribute = telemarkeDao.selectNetworkById3(tid);
        }else if ("Z".equals(tid.substring(0,1))){
            developDao.updateBytrackId(distribute);
            customerOrderDao.updateBytrackId(distribute);
            distribute = developDao.selectDeveolpById3(tid);
        }
        Enterprise enterprise = new Enterprise();
        enterprise.setLastFollowName(distribute.getLastFollowName());
        enterprise.setAddName(distribute.getProposer());
        enterprise.setCorporateName(distribute.getCorporateName());
        enterprise.setCorporatePhone(distribute.getCorporatePhone());
        enterprise.setCorporatePhone2(distribute.getCorporatePhone2());
        enterprise.setCorporatePhone3(distribute.getCorporatePhone3());
        enterprise.setTrackId(tid);
        enterprise.setName(distribute.getName());
        enterprise.setSource(distribute.getSource().toString());
        enterpriseDao.insertEnterprise(enterprise);
        Distribute distribute3 = (Distribute) map.get("distribute");
        List<Distribute> counducts = distribute3.getConducts();
        List list = new ArrayList();
        for (int i=0;i<counducts.size();i++){
            Distribute distribute2 = (Distribute) counducts.get(i);
            Business business = new Business();
            business.setBusinessNo(distribute2.getBusinessNo());
            business.setConduct(distribute2.getConduct());
            business.setContractNo(distribute3.getContractNo());
            business.setCorporatePhone(distribute3.getCorporatePhone());
            business.setDealTime(distribute3.getDealTime());
            business.setPay(distribute2.getPay());
            list.add(business);
        }
        businessDao.insertBusiness(list);
        return dealOrderDao.updateDealOrder((Distribute) map.get("distribute"));
    }

    @Override
    public PageInfo<CashierVo> cashierCompleteLis(Map map) {
        Set set = userDao.findRoleByUserName2((String) map.get("username"));
        Integer pageNum = (Integer) map.get("pageNum");
        Integer limit = (Integer) map.get("limit");
        if ((Integer) map.get("orderState") == 0){
            map.put("orderState",0);
        }else {
            map.put("orderState",null);
        }
        for (Object str : set) {
            if (str.equals(Department.FINANCE.value) || str.equals(Department.ADMIN.value)) {//财务
                PageHelper.startPage(pageNum, limit);
                List<CashierVo> CashierVo = networkDao.cashierListNetwork(map);
                PageInfo result = new PageInfo(CashierVo);
                return result;
            }
        }
        return null;
    }

    @Override
    public CashierVo recordingPop(Distribute distribute) {
        return dealOrderDao.qureyOrderAll(distribute);
    }

    @Override
    public PageInfo<Distribute> dealListNetwork(Map map) {
        Set set = userDao.findRoleByUserName2((String) map.get("username"));
        Integer pageNum = (Integer) map.get("pageNum");
        Integer limit = (Integer) map.get("limit");
        PageHelper.startPage(pageNum, limit);
        List<Distribute> CashierVo = networkDao.dealListNetwork(map);
        PageInfo result = new PageInfo(CashierVo);
        return result;
    }

    @Override
    public PageInfo<Distribute> subordinateList(Map map) {
        Integer pageNum = (Integer) map.get("pageNum");
        Integer limit = (Integer) map.get("limit");
        if ("K".equals(map.get("pool").toString())){
            PageHelper.startPage(pageNum, limit);
            List<Distribute> CashierVo = networkDao.queryNetworkByLastName(map);
            PageInfo result = new PageInfo(CashierVo);
            return result;
        }else if ("L".equals(map.get("pool").toString())){
            PageHelper.startPage(pageNum, limit);
            List<Distribute> CashierVo = telemarkeDao.queryTelemarkeByLastName(map);
            PageInfo result = new PageInfo(CashierVo);
            return result;
        }else if ("Z".equals(map.get("pool").toString())){
            PageHelper.startPage(pageNum, limit);
            List<Distribute> CashierVo = developDao.developList(map);
            PageInfo result = new PageInfo(CashierVo);
            return result;
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
            distribute1.setStatus(10);
            distribute1.setBranch(distribute.get(i).getBranch());
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
        String str = trackId.substring(0,1);
        if ("K".equals(str)){
            return networkFollowDao.qureyFollowList(id);
        }else if ("L".equals(str)){
            return telemarkeFollowDao.qureyFollowList(id);
        }else if ("Z".equals(str)){
            return developFollowDao.qureyFollowList(id);
        }
        return null;
    }

    @Override
    public Distribute qureyCustomer(Integer id, String trackId) {
        String str = trackId.substring(0,1);
        if ("K".equals(str)){
            return networkDao.selectNetworkById(id);
        }else if ("L".equals(str)){
            return telemarkeDao.selectNetworkById(id);
        }else if ("Z".equals(str)){
            return developDao.selectDeveolpById(id);
        }
        return null;
    }

    @Override
    public List enterpriseList(Map map) {
        return enterpriseDao.enterpriseList(map);
    }

    @Override
    public int addEnterprise(Map map) {
        Enterprise enterprise = (Enterprise) map.get("enterprise");
        enterprise.setAddName((String) map.get("name"));
        return enterpriseDao.insertEnterprise(enterprise);
    }

    @Override
    public List business(Map map) {
        List list = new ArrayList();
        list.add(map.get("corporatePhone"));
        list.add(map.get("corporatePhone2"));
        list.add(map.get("corporatePhone3"));
        return businessDao.qureyBusiness(list);
    }

    @Override
    public int addBusiness(Map map) {
        DealOrder order = (DealOrder) map.get("dealOrder");
        return businessDao.insertBusiness2(order);
    }

    @Override
    @Transactional
    public int returnPool(Map map) {
        Distribute distribute = (Distribute) map.get("distribute");
        DistributeFollow networkFollow;
        Integer status = distribute.getStatus();
        String str = null;
        if (7 == status){
            str = map.get("name")+"提交“无效”退单";
        }else if (11 == status){
            str = map.get("name")+"提交“有效（无法办理）”退单";
        }
        String trid = distribute.getTrackId().substring(0, 1);;
        networkFollow = new DistributeFollow();
        networkFollow.setFollowName((String) map.get("name"));
        networkFollow.setNetworkId(distribute.getId());
        networkFollow.setFollowResult(str);
        if ("K".equals(trid)){
            networkFollowDao.insertNetworkFollow(networkFollow);
            networkFollow.setImgUrl(distribute.getImgUrl());
            networkFollow.setRecord(distribute.getRecord());
            networkFollow.setFollowResult(distribute.getLastFollowResult());
            networkFollowDao.insertNetworkFollow(networkFollow);
        }else if ("L".equals(trid)){
            telemarkeFollowDao.insertNetworkFollow(networkFollow);
            distribute.setIssue(1);
            distribute.setStatus(1);
            distribute.setSign(0);
            networkFollow.setImgUrl(distribute.getImgUrl());
            networkFollow.setRecord(distribute.getRecord());
            networkFollow.setFollowResult(distribute.getLastFollowResult());
            telemarkeFollowDao.insertNetworkFollow(networkFollow);
            distribute.setLastFollowTime(null);
            //distribute.setLastFollowResult(distribute.getLastFollowResult());
            distribute.setReceivingTime(null);
            distribute.setOverdueTime(null);
            networkFollow.setImgUrl(distribute.getImgUrl());
            networkFollow.setFollowResult(distribute.getLastFollowResult());
            return telemarkeDao.updateBytrackId2(distribute);
        }
        distribute.setIssue(2);
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
            distribute.setOverdueTime(null);
            distribute.setOverrun(1);
        }else {
            distribute.setStatus(10);
            distribute.setOverdueTime(DateUtil.getOverTime(86400000));
        }
        networkFollow.setFollowResult(str);
        networkFollowDao.insertNetworkFollow(networkFollow);
        networkFollow.setImgUrl(distribute.getImgUrl());
        networkFollow.setFollowResult(distribute.getLastFollowResult());
        networkFollowDao.insertNetworkFollow(networkFollow);
        distribute.setIssue(1);
        distribute.setInvalid(1);
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
        Integer status = distribute.getStatus();
        if(status == 7){
            distribute.setSign(0);
            distribute.setStatus(1);
        }else if (status == 11){
            distribute.setSign(2);
            distribute.setStatus(0);
        }
        distribute.setLastFollowName(null);
        distribute.setReceivingTime(null);
        distribute.setIssue(1);
        distribute.setInvalid(1);
        distribute.setOverdueTime(null);
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

    @Override
    @Transactional
    public int activation(Map map) {
        String str = map.get("name")+"激活客户";
        Distribute  distribute = (Distribute) map.get("distribute");
        String strid =  distribute.getTrackId().substring(0,1);
        distribute.setActivationName((String) map.get("name"));
        distribute.setLastFollowName((String) map.get("name"));
        distribute.setActivation(1);
        distribute.setStatus(10);
        distribute.setOverrun(0);
        distribute.setActivationTime(DateUtil.getDate());
        DistributeFollow networkFollow = new DistributeFollow();
        networkFollow.setFollowName((String) map.get("name"));
        networkFollow.setNetworkId(distribute.getId());
        networkFollow.setFollowResult(str);
        if ("K".equals(strid)){
            Distribute distribute1 = networkDao.selectNetworkById(distribute.getId());;
            if (distribute1.getSign() == 1){
                distribute.setOverdueTime(DateUtil.getOverTime(259200000));
            }
            if (distribute1.getActivation() == 0){
                networkFollowDao.insertNetworkFollow(networkFollow);
            }
            return networkDao.updateNetwork(distribute);
        }else if ("L".equals(strid)){
            Distribute distribute1 = telemarkeDao.selectNetworkById(distribute.getId());
            if (distribute1.getSign() == 1){
                distribute.setOverdueTime(DateUtil.getOverTime(259200000));
            }
            if (distribute1.getActivation() == 0){
                telemarkeFollowDao.insertNetworkFollow(networkFollow);
            }
            return telemarkeDao.updateworkOverdueTime(distribute);
        }else if ("Z".equals(strid)){
            return developDao.updateDevelop(distribute);
        }
        return 0;
    }

    @Override
    public Distribute qureyTrackId(String trackId) {
        System.out.println(trackId);
        return networkDao.selectNetworkById3(trackId);
    }
}
