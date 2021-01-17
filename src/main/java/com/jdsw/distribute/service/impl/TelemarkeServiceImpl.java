package com.jdsw.distribute.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.jdsw.distribute.dao.TelemarkeDao;
import com.jdsw.distribute.dao.TelemarkeFollowDao;
import com.jdsw.distribute.dao.UserDao;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.model.Excel;
import com.jdsw.distribute.model.User;
import com.jdsw.distribute.service.TelemarkeService;
import com.jdsw.distribute.util.DateUtil;
import com.jdsw.distribute.util.FileUtil;
import com.jdsw.distribute.util.Rand;
import com.jdsw.distribute.util.excelRead;
import com.jdsw.distribute.vo.CashierVo;
import com.jdsw.distribute.vo.RecordingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TelemarkeServiceImpl implements TelemarkeService {
    @Autowired
    private TelemarkeDao telemarkDao;
    @Autowired
    private TelemarkeFollowDao telemarkeFollowDao;
    @Autowired
    private UserDao userDao;
    @Override
    public PageInfo<Distribute> armyListPoolList(int pageNum, int limit, String content, String strtime, String endtime,String username) {
        PageHelper.startPage(pageNum, limit);
        Set set = userDao.findRoleByUserName(username);
        List<Distribute> Network = null;
        for (Object str : set) {
            if (Integer.parseInt((String) str) == 1){
                Network = telemarkDao.armyListPoolList(content,strtime,endtime);
            }else if (Integer.parseInt((String) str) == 6 || Integer.parseInt((String) str) == 4){
                Network = telemarkDao.armyListPoolList2(content,strtime,endtime);
            }
        }

        PageInfo result = new PageInfo(Network);
        return result;
    }
    @Override
    public int insertTelemarke(Distribute distribute,String username,String name) {
        String trackId = Rand.getTrackId("WL");//获得跟踪单号
        distribute.setTrackId(trackId);
        Set set = userDao.findRoleByUserName2(username);
        User user = userDao.findByUserName(username);
        for (Object str : set) {
            if (str.equals("业务员")) {
                distribute.setLastFollowName(user.getName());
                distribute.setFirstFollowName(user.getName());
                distribute.setIssue(1);
                distribute.setAppoint(0);
                distribute.setStatus(10);
                distribute.setActivation(0);
                return telemarkDao.insertTelemarke(distribute);
            }
            if (str.equals("部门主管")){
                distribute.setLeaderName(name);
                distribute.setIssue(0);
                distribute.setAppoint(0);
                distribute.setStatus(5);
                distribute.setActivation(0);
                return telemarkDao.insertTelemarke(distribute);
            }
        }
        distribute.setAppoint(0);
        distribute.setIssue(0);
        distribute.setStatus(0);
        distribute.setActivation(0);
        return telemarkDao.insertTelemarke(distribute);
    }
    @Override
    public int excelTelemarke(MultipartFile file,String username) throws Exception {
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
        Set set = userDao.findRoleByUserName2(username);
        User user = userDao.findByUserName(username);
        for (Object str : set) {
            if (str.equals("业务员")){
                for (int i = 0;i<result.size();i++){
                    Object ob = result.get(i);
                    Map map = JSON.parseObject(JSON.toJSONString(ob),Map.class);
                    String trackId = Rand.getTrackId("WL");//获得跟踪单号
                    map.put("trackId",trackId);
                    map.put("lastFollowName",user.getName());
                    map.put("firstFollowName",user.getName());
                    map.put("issue",1);
                    map.put("status",10);
                    map.put("appoint",0);
                    map.put("activation",0);
                    ls.add(map);
                }

                return telemarkDao.excelTelemarke(ls);
            }if (str.equals("部门主管")){//主管
                for (int i = 0;i<result.size();i++){
                    Object ob = result.get(i);
                    Map map = JSON.parseObject(JSON.toJSONString(ob),Map.class);
                    String trackId = Rand.getTrackId("WL");//获得跟踪单号
                    map.put("trackId",trackId);
                    map.put("leaderName",user.getName());
                    map.put("lastFollowName","");
                    map.put("firstFollowName","");
                    map.put("issue",0);
                    map.put("appoint",0);
                    map.put("leaderSign",1);
                    map.put("status",5);
                    map.put("activation",0);
                    ls.add(map);
                }
                return telemarkDao.excelTelemarke(ls);

            }
        }
        for (int i = 0;i<result.size();i++){
            Object ob = result.get(i);
            Map map = JSON.parseObject(JSON.toJSONString(ob),Map.class);
            String trackId = Rand.getTrackId("WL");//获得跟踪单号
            map.put("trackId",trackId);
            map.put("issue",0);
            map.put("status",0);
            map.put("activation",0);
            ls.add(map);
        }
        return telemarkDao.excelTelemarke(ls);
    }
    @Override
    public int deleteTelemarke(Distribute distribute) {
        return telemarkDao.deleteTelemarke(distribute);
    }

    @Override
    public int updateTelemarke(Distribute distribute) {
        return telemarkDao.updateTelemarke(distribute);
    }
    @Override
    public List<Map> qureyTelemarke(Integer id) {
        return telemarkDao.qureyTelemarke(id);
    }


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
            distribute.setStatus(0);
            distribute.setBranch(network.get(i).getBranch());
            if (StringUtil.isNotEmpty(network.get(i).getFirstFollowName())){
                distribute.setFirstFollowName(network.get(i).getFirstFollowName());
                distribute.setLastFollowName(network.get(i).getFirstFollowName());
                distribute.setOverdueTime(DateUtil.getOverTime(600000));
                distribute.setAppoint(1);
                distribute.setStatus(10);
                networkFollow.setFollowName(name);
                networkFollow.setNetworkId(network.get(i).getId());
                networkFollow.setFollowResult(str);
                ls.add(networkFollow);
            }
            ld.add(distribute);
        }
        if(ls.size() > 0){
            telemarkeFollowDao.insertNetworkFollow2(ls);
        }
        return telemarkDao.appoint(ld);
    }
    @Override
    @Transactional
    public int orderTaking(Distribute network) {
        Distribute network2 = telemarkDao.selectNetworkById(network.getId());//查询是否是已经接过的线索
        Integer status = network2.getStatus();
        if (status == 1 || status == 2){//如果是跟进无效或者超时的
            int o = telemarkDao.updateNetworkLastFollowName(network);
            if (o > 0){
                return 2;
            }
        }else {
            network.setOverdueTime(DateUtil.getNextDay());
            network.setActivation(0);
            network.setStatus(10);
            telemarkDao.updateworkOverdueTime(network);//修改激活时间
            int i = telemarkDao.updateNetworkFirstFollowName(network);
            if (i > 0){
                return 1;
            }
        }
        return 0;
    }

    @Override
    public PageInfo<Distribute> queryTelemarkeByLastName(int pageNum, int limit,String content, String strtime, String endtime,String lastFollowName,String username) {
        Set set = userDao.findRoleByUserName2(username);
        for (Object str : set) {
            if (str.equals("部门主管")) {//主管
                PageHelper.startPage(pageNum, limit);
                List<Distribute> Network = telemarkDao.queryTelemarkeByLastName(content,strtime,endtime,lastFollowName);
                PageInfo result = new PageInfo(Network);
                return result;
            }else if (str.equals("业务员")){//业务员
                PageHelper.startPage(pageNum, limit);
                List<Distribute> Network = telemarkDao.queryTelemarkeByLastName2(content,strtime,endtime,lastFollowName);
                PageInfo result = new PageInfo(Network);
                return result;
            }
        }
        return null;
    }

    @Override
    public PageInfo<Distribute> pendingNetworkList(int pageNum, int limit, String content, String strtime, String endtime, String lastFollowName) throws ParseException {
        PageHelper.startPage(pageNum, limit);
        List<Distribute> Network = telemarkDao.pendingNetworkList(content,strtime,endtime,lastFollowName);
        PageInfo result = new PageInfo(Network);
        return result;
    }


    @Override
    public int overTime(Distribute network) {
        network.setStatus(3);
        network.setLastFollowName("");
        return telemarkDao.overTime(network);
    }

    @Override
    @Transactional
    public int followupNetwork(DistributeFollow distributeFollow) {
        Distribute distribute = new Distribute();
        distribute.setOverdueTime(distributeFollow.getFollowTime());
        distribute.setActivation(1);
        distribute.setId(distributeFollow.getNetworkId());
        int repool = distributeFollow.getReturnPool();
        if (repool == 1){
            distribute.setStatus(2);
        }
        telemarkDao.updateworkOverdueTime(distribute);
        telemarkeFollowDao.updateFolloupNetwork(distributeFollow);
        return telemarkeFollowDao.insertNetworkFollow(distributeFollow);
    }

    @Override
    public int uploadImg(MultipartFile img, HttpServletRequest request) {
        String fileName = img.getOriginalFilename();
        //设置文件上传路径
        String filePath = "E:\\upimg\\";
        try {
            FileUtil.uploadFile(img.getBytes(), filePath, fileName);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    @Transactional
    public int SubmitRecordingNetwork(List<Distribute> distribute) {
        Distribute distribute2;
        for (int i=0;i<distribute.size();i++){
            distribute.get(i).setStatus(3);
            distribute2 = telemarkDao.selectNetworkById(distribute.get(i).getId());
            telemarkDao.insertDealOrder(distribute2);

        }
        return telemarkDao.SubmitRecordingNetwork(distribute);
    }
    @Override
    public int Unsettled() {
        List<Distribute> ls = telemarkDao.Unsettled();
        String date = DateUtil.getDateTime();
        for (int i=0;i<ls.size();i++){

        }
        return 0;
    }

    @Override
    public int transferNetwork(Distribute distribute) {
        return telemarkDao.updateNetworkLastFollowName(distribute);
    }

    @Override
    @Transactional
    public int assign(List<Distribute> distribute,String username,String name) {
        Distribute distribute1 = null;
        String str = "给"+distribute.get(0).getLeaderName()+"转交了一条线索";
        DistributeFollow networkFollow;
        List<Distribute> ld = new ArrayList<>();
        List<DistributeFollow> ls = new ArrayList<>();
        for (int i = 0 ;i<distribute.size();i++){
            networkFollow = new DistributeFollow();
            networkFollow.setFollowName(name);
            networkFollow.setNetworkId(distribute.get(i).getId());
            networkFollow.setFollowResult(str);
            ls.add(networkFollow);
            distribute1 = new Distribute();
            distribute1.setFirstFollowName(distribute.get(i).getLeaderName());
            distribute1.setLastFollowName(distribute.get(i).getLeaderName());
            distribute1.setLeaderSign(1);
            distribute1.setIssue(1);
            distribute1.setStatus(10);
            distribute1.setOverdueTime(DateUtil.getOverTime(86400000));
            distribute1.setId(distribute.get(i).getId());
            ld.add(distribute1);
            telemarkeFollowDao.insertNetworkFollow2(ls);
        }
        return telemarkDao.assign(ld);
    }

    @Override
    @Transactional
    public int customerTransfer(List<Distribute> distribute,String username,String name) {
        Distribute distribute1 = null;
        String str = "给"+distribute.get(0).getLeaderName()+"转交了一条线索";
        List<Distribute> ld = new ArrayList<>();
        List<DistributeFollow> ls = new ArrayList<>();
        DistributeFollow networkFollow;
        for (int i = 0 ;i<distribute.size();i++){
            networkFollow = new DistributeFollow();
            networkFollow.setFollowName(name);
            networkFollow.setNetworkId(distribute.get(i).getId());
            networkFollow.setFollowResult(str);
            distribute1 = new Distribute();
            distribute1.setLeaderName(distribute.get(i).getLeaderName());
            distribute1.setLeaderSign(0);
            distribute1.setIssue(1);
            distribute1.setStatus(5);
            distribute1.setId(distribute.get(i).getId());
            ld.add(distribute1);
            ls.add(networkFollow);
            telemarkeFollowDao.insertNetworkFollow2(ls);
        }
        return telemarkDao.assign(ld);
    }

    @Override
    public List<DistributeFollow> qureyFollowList(Integer id) {
        return telemarkeFollowDao.qureyFollowList(id);
    }
    @Override
    public int setOvertime(Distribute distribute) {
        int sing = telemarkDao.querySign(distribute.getId());
        if (sing > 0){
            distribute.setStatus(2);
            distribute.setLeaderName("");
        }
        distribute.setOverdueTime("");
        distribute.setLastFollowName("");
        distribute.setLeaderSign(0);
        return telemarkDao.setOvertime(distribute);
    }
    @Override
    public PageInfo<Distribute> statusList(int pageNum, int limit, Integer status,String name) {
        Distribute distribute1 = new Distribute();
        distribute1.setLastFollowName(name);
        distribute1.setStatus(status);
        PageHelper.startPage(pageNum, limit);
        List<Distribute> distribute = telemarkDao.statusList(distribute1);
        PageInfo result = new PageInfo(distribute);
        return result;
    }
}
