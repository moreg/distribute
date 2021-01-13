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
        User user = userDao.findByUserName(username);
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
    public int insertTelemarke(Distribute distribute) {
        String trackId = Rand.getTrackId("WL");//获得跟踪单号
        distribute.setTrackId(trackId);
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
        Set set = userDao.findRoleByUserName(username);
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
                return telemarkDao.excelTelemarke(ls);
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
    public PageInfo<Distribute> grabbingOrdersList(int pageNum, int limit, String content, String strtime, String endtime) {
        PageHelper.startPage(pageNum, limit);
        List<Distribute> Network = telemarkDao.grabbingOrdersList(content,strtime,endtime);
        PageInfo result = new PageInfo(Network);
        return result;
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
            telemarkeFollowDao.insertNetworkFollow2(ls);
        }
        System.out.println(ld);
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
            telemarkDao.updateworkOverdueTime(network);//修改激活时间
            int i = telemarkDao.updateNetworkFirstFollowName(network);
            if (i > 0){
                return 1;
            }
        }
        return 0;
    }

    @Override
    public PageInfo<Distribute> queryTelemarkeByLastName(int pageNum, int limit,String content, String strtime, String endtime,String lastFollowName) {
        PageHelper.startPage(pageNum, limit);
        List<Distribute> Network = telemarkDao.queryTelemarkeByLastName(content,strtime,endtime,lastFollowName);
        PageInfo result = new PageInfo(Network);
        return result;
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
        int releader = distributeFollow.getReturnLeader();
        if (repool == 1){
            distribute.setOverdueTime("");
            distribute.setActivation(0);

        }else if (releader == 1){
            distribute.setOverdueTime("");
            distribute.setActivation(0);
            distribute.setStatus(5);
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
        Distribute distribute2 = null;
        for (int i=0;i<distribute.size();i++){
            distribute2 = new Distribute();
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
    public int assign(List<Distribute> distribute) {
        return telemarkDao.assign(distribute);
    }

    @Override
    public int customerTransfer(List<Distribute> distribute) {
        return telemarkDao.assign(distribute);
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
