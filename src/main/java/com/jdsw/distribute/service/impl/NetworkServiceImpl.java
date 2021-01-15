package com.jdsw.distribute.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.dao.NetworkDao;
import com.jdsw.distribute.dao.NetworkFollowDao;
import com.jdsw.distribute.dao.UserDao;
import com.jdsw.distribute.model.*;
import com.jdsw.distribute.service.NetworkService;
import com.jdsw.distribute.util.*;
import com.github.pagehelper.util.StringUtil;

import com.jdsw.distribute.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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



@Service
public class NetworkServiceImpl implements NetworkService {

    @Autowired
    private NetworkDao networkDao;
    @Autowired
    private NetworkFollowDao networkFollowDao;
    @Autowired
    private UserDao userDao;
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
            distribute.setFirstFollowName(network.get(i).getFirstFollowName());
            distribute.setLastFollowName(network.get(i).getFirstFollowName());
            distribute.setOverdueTime(DateUtil.getOverTime(600000));
            distribute.setAppoint(0);
            distribute.setBranch(network.get(i).getBranch());
            if (StringUtil.isEmpty(network.get(i).getBranch())){
                distribute.setAppoint(1);
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
    public int orderTaking(Distribute network) {
        Distribute network2 = networkDao.selectNetworkById(network.getId());
        Integer status = network2.getStatus();
        if (status == 1 || status == 2){
           /* int o = networkDao.updateNetworkLastFollowName(network);
            if (o > 0){
                return 2;
            }*/
        }else {
            int i = networkDao.updateNetworkFirstFollowName(network);
            if (i > 0){
                return 1;
            }
        }
        return 0;
    }

    @Override
    public PageInfo<Distribute> queryNetworkByLastName(int pageNum, int limit,String content, String strtime, String endtime,String lastFollowName) throws ParseException {
        PageHelper.startPage(pageNum, limit);
        List<Distribute> Network = networkDao.queryNetworkByLastName(content,strtime,endtime,lastFollowName);
        PageInfo result = new PageInfo(Network);
        return result;
    }

    @Override
    public PageInfo<Distribute> pendingNetworkList(int pageNum, int limit, String content, String strtime, String endtime, String lastFollowName) throws ParseException  {
        PageHelper.startPage(pageNum, limit);
        List<Distribute> Network = networkDao.pendingNetworkList(content,strtime,endtime,lastFollowName);
        PageInfo result = new PageInfo(Network);
        return result;
    }


    @Override
    public PageInfo<Distribute> airForcePoolList(int pageNum, int limit, Distribute network, String content, String strtime, String endtime,String username) {
        Set set = userDao.findRoleByUserName(username);
        for (Object str : set) {
            if (Integer.parseInt((String) str) == 1){//线索管理员
                PageHelper.startPage(pageNum,limit);
                List<Distribute> Network = networkDao.airForcePoolList(content,strtime,endtime);
                PageInfo result = new PageInfo(Network);
                return result;
            }else if (Integer.parseInt((String) str) == 6 || Integer.parseInt((String) str) == 4){//4:主管，6：业务员
                PageHelper.startPage(pageNum,limit);
                List<Distribute> Network = networkDao.airForcePoolList2(content,strtime,endtime);
                PageInfo result = new PageInfo(Network);
                return result;
            }
        }
        return null;

    }

    @Override
    public PageInfo<Distribute> pendingPoolList(int pageNum, int limit, Distribute network, String content, String strtime, String endtime, String username) {
        PageHelper.startPage(pageNum, limit);
        Set set = userDao.findRoleByUserName(username);
        User user = userDao.findByUserName(username);
        List<Distribute> Network = null;
        for (Object str : set) {
            if (Integer.parseInt((String) str) == 1){
                Network = networkDao.pendingPoolList(content,strtime,endtime);
            }
        }
        PageInfo result = new PageInfo(Network);
        return result;
    }

    @Override
    public PageInfo<Distribute> grabbingOrdersList(int pageNum, int limit, String content, String strtime, String endtime) {
        PageHelper.startPage(pageNum, limit);
        List<Distribute> Network = networkDao.grabbingOrdersList(content,strtime,endtime);
        PageInfo result = new PageInfo(Network);
        return result;
    }


    @Override
    public int insertNetwoork(Distribute distribute,String username) {
        String trackId = Rand.getTrackId("WL");//获得跟踪单号
        distribute.setTrackId(trackId);
        Set set = userDao.findRoleByUserName(username);
        User user = userDao.findByUserName(username);
        for (Object str : set) {
            if (Integer.parseInt((String) str) == 6){
                distribute.setLastFollowName(user.getName());
                distribute.setFirstFollowName(user.getName());
                distribute.setIssue(1);
                distribute.setAppoint(0);
                return networkDao.insertNetwoork(distribute);
            }
        }
        distribute.setIssue(0);
        return networkDao.insertNetwoork(distribute);
    }

    @Override
    public int excelNetwork(MultipartFile file,String username) throws Exception {
        String filePath = "E:\\upexl\\";
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String newFileName = file.getOriginalFilename();
        File newFile = new File(filePath + newFileName);
        //复制操作
        file.transferTo(newFile);
        List<Object> result = excelRead.ReadExcelByPOJO(newFile.toString(),2,5, Excel.class);
        List ls = new ArrayList();
        Set set = userDao.findRoleByUserName(username.trim());
        User user = userDao.findByUserName(username);
        for (Object str : set) {
            if (Integer.parseInt((String) str) == 6){
                for (int i = 0;i<result.size();i++){
                    Object ob = result.get(i);
                    Map map = JSON.parseObject(JSON.toJSONString(ob),Map.class);
                    String trackId = Rand.getTrackId("WL");//获得跟踪单号
                    map.put("trackId",trackId);
                    map.put("lastFollowName",user.getName());
                    map.put("firstFollowName",user.getName());
                    map.put("issue",1);
                    map.put("appoint",0);
                    ls.add(map);
                }
                return networkDao.excelNetwork(ls);
            }
        }
        for (int i = 0;i<result.size();i++){
            Object ob = result.get(i);
            Map map = JSON.parseObject(JSON.toJSONString(ob),Map.class);
            String trackId = Rand.getTrackId("WL");//获得跟踪单号
            map.put("trackId",trackId);
            map.put("issue",0);
            ls.add(map);
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
    public List<Map> qureyNetwork(Integer id) {
        return networkDao.qureyNetwork(id);
    }

    @Override
    public int overTime(Distribute network) {
        network.setStatus(3);
        network.setLastFollowName("");
        return networkDao.overTime(network);
    }

    @Override
    @Transactional
    public int followupNetwork(DistributeFollow networkFollow) {
        Distribute distribute = new Distribute();
        if(networkFollow.getReturnPool() == 1){
            distribute = networkDao.selectNetworkById(networkFollow.getNetworkId());
            if (distribute.getInvalid() != null && distribute.getInvalid() == 1){
                distribute.setStatus(5);
                distribute.setInvalid(0);
            }else {
                distribute.setStatus(1);
                distribute.setInvalid(1);
            }
        }
        networkDao.SubmitRecordingNetwork(distribute);
        networkFollowDao.updateFolloupNetwork(networkFollow);
        return networkFollowDao.insertNetworkFollow(networkFollow);
    }
    @Override
    @Transactional
    public int SubmitRecordingNetwork(List<Distribute> distribute) {
        for (int i=0;i<distribute.size();i++){
            Distribute distribute2 = new Distribute();
            distribute.get(i).setStatus(3);
            distribute2 = networkDao.selectNetworkById(distribute.get(i).getId());
            networkDao.insertDealOrder(distribute2);
        }




        return networkDao.SubmitRecordingNetwork2(distribute);
    }

    @Override
    @Transactional
    public int UpdateRecordingNetwork(Distribute distribute) {
        distribute.setStatus(4);
        networkDao.SubmitRecordingNetwork(distribute);
        return networkDao.UpdateRecordingNetwork(distribute);
    }

    @Override
    public PageInfo<CashierVo> cashierListNetwork(int pageNum, int limit, String content, String strtime, String endtime) {
        PageHelper.startPage(pageNum, limit);
        List<CashierVo> CashierVo = networkDao.cashierListNetwork(content,strtime,endtime);
        PageInfo result = new PageInfo(CashierVo);
        return result;

    }

    @Override
    public int Unsettled() {
        List<Distribute> ls = networkDao.Unsettled();
        String date = DateUtil.getDateTime();
        for (int i=0;i<ls.size();i++){
            //ls.get(i).getOverdueTime();
            ls.get(i).getId();
        }
        return 0;
    }

    @Override
    public int transferNetwork(List<Distribute> distribute) {
        return networkDao.updateNetworkLastFollowName(distribute);
    }

    @Override
    public int customerTransfer(List<Distribute> distribute) {
        return networkDao.updateNetworkLastFollowName(distribute);
    }

    @Override
    public int setOverdueTime(Distribute distribute) {
        distribute.setOverdueTime(DateUtil.getOverTime(distribute.getMsec()));
        return networkDao.setOverdueTime(distribute);
    }

    @Override
    public List<DistributeFollow> qureyFollowList(Integer id) {
        return networkFollowDao.qureyFollowList(id);
    }

    @Override
    public List<Distribute> notice() throws Exception {
        List<Distribute> ls = networkDao.notice();
        List<Distribute> ls2 = new ArrayList<>();
        for (int i=0;i<ls.size();i++){
            if (DateUtil.getOvertimeBoo(ls.get(i).getOverdueTime())){
                Distribute d = new Distribute();
                d.setOverdueTime(ls.get(i).getOverdueTime());
                d.setId(ls.get(i).getId());
                d.setTrackId(ls.get(i).getTrackId());
                d.setLastFollowName(ls.get(i).getLastFollowName());
                ls2.add(d);
            }
        }
        return ls2;
    }

    @Override
    public int chargeback(Distribute distribute) {
        distribute.setStatus(8);
        return networkDao.SubmitRecordingNetwork(distribute);
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
        distribute.setStatus(0);
        return networkDao.SubmitRecordingNetwork(distribute);
    }


}
