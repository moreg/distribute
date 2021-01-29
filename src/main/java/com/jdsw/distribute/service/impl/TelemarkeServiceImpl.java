package com.jdsw.distribute.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.jdsw.distribute.dao.TelemarkeDao;
import com.jdsw.distribute.dao.TelemarkeFollowDao;
import com.jdsw.distribute.dao.UserDao;
import com.jdsw.distribute.enums.Department;
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
        List<Distribute> Network = telemarkDao.armyListPoolList(content,strtime,endtime);
        PageInfo result = new PageInfo(Network);
        return result;
    }

    @Override
    public PageInfo<Distribute> grabbingPool(Map map) {
        Integer pageNum = (Integer) map.get("pageNum");
        Integer limit = (Integer) map.get("limit");
        PageHelper.startPage(pageNum, limit);
        List<Distribute> Network = telemarkDao.grabbingPool(map);
        PageInfo result = new PageInfo(Network);
        return result;
    }

    @Override
    @Transactional
    public int insertTelemarke(Distribute distribute,String username,String name) {
        String trackId = Rand.getTrackId("LJ");//获得跟踪单号
        String str1 = name+"新建线索";
        distribute.setTrackId(trackId);
        Set set = userDao.findRoleByUserName2(username);
        DistributeFollow networkFollow = new DistributeFollow();
        networkFollow.setFollowName(name);
        networkFollow.setNetworkId(distribute.getId());
        networkFollow.setFollowResult(str1);
        distribute.setActivation(0);//默认未激活
        distribute.setSign(1);
        distribute.setProposer(name);
        for (Object str : set) {
           if (str.equals(Department.ARMCUSTOMER.value)){//线索管理员
                distribute.setLastFollowName(distribute.getLastFollowName());
                distribute.setFirstFollowName(distribute.getLastFollowName());
                distribute.setIssue(0);
                distribute.setStatus(0);
                if (StringUtil.isNotEmpty(distribute.getLastFollowName())){
                    distribute.setIssue(1);
                    distribute.setStatus(10);
                    String leader2 = userDao.queryDepartment3(distribute.getLastFollowName());
                    distribute.setLeaderName(leader2);
                }else if (StringUtil.isNotEmpty(distribute.getBranch())){
                    distribute.setIssue(1);
                }
            }
        }
        telemarkDao.insertTelemarke(distribute);
        networkFollow.setNetworkId(distribute.getId());
        telemarkeFollowDao.insertNetworkFollow(networkFollow);
        networkFollow.setFollowResult(distribute.getLastFollowResult());
        return telemarkeFollowDao.insertNetworkFollow(networkFollow);
    }
    @Override
    @Transactional
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
        List<Object> result = excelRead.ReadExcelByPOJO(newFile.toString(),2,9, Excel.class);
        List ls = new ArrayList();
        Set set = userDao.findRoleByUserName2(username);
        User user = userDao.findByUserName(username);
        for (Object str : set) {
            if (str.equals(Department.SALESMAN.value)){//业务员
                for (int i = 0;i<result.size();i++){
                    Object ob = result.get(i);
                    Map map = JSON.parseObject(JSON.toJSONString(ob),Map.class);
                    String trackId = Rand.getTrackId("LJ");//获得跟踪单号
                    map.put("trackId",trackId);
                    map.put("lastFollowName",user.getName());
                    map.put("firstFollowName",user.getName());
                    map.put("issue",1);
                    map.put("status",10);
                    map.put("appoint",1);
                    map.put("activation",0);
                    ls.add(map);
                }
            }else if (str.equals(Department.CHARGE.value)){//主管
                for (int i = 0;i<result.size();i++){
                    Object ob = result.get(i);
                    Map map = JSON.parseObject(JSON.toJSONString(ob),Map.class);
                    String trackId = Rand.getTrackId("LJ");//获得跟踪单号
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
            }else {
                for (int i = 0;i<result.size();i++){
                    Object ob = result.get(i);
                    Map map = JSON.parseObject(JSON.toJSONString(ob),Map.class);
                    String trackId = Rand.getTrackId("LJ");//获得跟踪单号
                    map.put("trackId",trackId);
                    map.put("lastFollowName","");
                    map.put("firstFollowName","");
                    map.put("issue",0);
                    map.put("appoint",0);
                    map.put("leaderSign",1);
                    map.put("status",0);
                    map.put("activation",0);
                    ls.add(map);
                }
            }
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
            distribute.setStatus(0);
            distribute.setBranch(network.get(i).getBranch());
            distribute.setLeaderName(leader);
            distribute.setLeaderSign(0);
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
    public int orderTaking(Distribute network,String name) {
        String str = name+"领取了线索";
        DistributeFollow networkFollow = new DistributeFollow();
        networkFollow.setFollowName(name);
        networkFollow.setNetworkId(network.getId());
        networkFollow.setFollowResult(str);
        telemarkeFollowDao.insertNetworkFollow(networkFollow);
        network.setLeaderSign(0);
        String leader = userDao.queryDepartment3(name);
        network.setLeaderName(leader);
        network.setOverdueTime(DateUtil.getNextDay());
        network.setActivation(0);
        network.setStatus(10);
        telemarkDao.updateworkOverdueTime(network);//修改激活时间
        int i = telemarkDao.updateNetworkFirstFollowName(network);
        if (i > 0){
                return 1;
        }
        return 0;
    }

    @Override
    public PageInfo<Distribute> queryTelemarkeByLastName(Map map) {
        Integer pageNum = (Integer) map.get("pageNum");
        Integer limit = (Integer) map.get("limit");
        Set set = userDao.findRoleByUserName2((String) map.get("username"));
        for (Object str : set) {
            if (str.equals(Department.CHARGE.value)) {//主管
                PageHelper.startPage(pageNum, limit);
                List<Distribute> Network = telemarkDao.queryTelemarkeByLastName(map);
                PageInfo result = new PageInfo(Network);
                return result;
            }else if (str.equals(Department.SALESMAN.value)){//普通员工
                map.put("status",10);
                PageHelper.startPage(pageNum, limit);
                List<Distribute> Network = telemarkDao.queryTelemarkeByLastName2(map);
                PageInfo result = new PageInfo(Network);
                return result;
            }
        }
        return null;
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
        int sing = telemarkDao.querySign(distribute.getTrackId());
        if (sing > 0){
            distribute.setStatus(2);
            distribute.setLeaderName(null);
        }else {
            distribute.setLastFollowName(null);
            distribute.setLeaderSign(1);
            distribute.setStatus(5);
        }
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
