package com.jdsw.distribute.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.dao.NetworkDao;
import com.jdsw.distribute.dao.NetworkFollowDao;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.model.Excel;
import com.jdsw.distribute.model.Role;
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

import java.util.*;



@Service
public class NetworkServiceImpl implements NetworkService {

    @Autowired
    private NetworkDao networkDao;
    @Autowired
    private NetworkFollowDao networkFollowDao;

    @Override
    @Transactional
    public int appoint(List<Distribute> network, String name) {
        //int nw = networkDao.appoint(network);

        Distribute distribute;
        DistributeFollow networkFollow;
        String str = "给"+network.get(0).getFirstFollowName()+"转交了一条线索";
        List<DistributeFollow> ls = new ArrayList<>();
        List<Distribute> ld = new ArrayList<>();
        for (int i=0;i<network.size();i++){
            distribute = new Distribute();
            networkFollow = new DistributeFollow();
            String trackId = Rand.getTrackId("WL");//获得跟踪单号
            distribute.setTrackId(trackId);
            distribute.setId(network.get(i).getId());
            distribute.setDepartment(network.get(i).getDepartment());
            distribute.setFirstFollowName(network.get(i).getFirstFollowName());
            if (StringUtil.isEmpty(network.get(i).getDepartment())){
                distribute.setDepartment("总部");
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
    public PageInfo<Distribute> queryNetworkByLastName(int pageNum, int limit,String content, String strtime, String endtime,String lastFollowName) {
        PageHelper.startPage(pageNum, limit);
        List<Distribute> Network = networkDao.queryNetworkByLastName(content,strtime,endtime,lastFollowName);
        PageInfo result = new PageInfo(Network);
        return result;
    }


    @Override
    public PageInfo<Distribute> airForcePoolList(int pageNum, int limit, Distribute network, String content, String strtime, String endtime) {
        PageHelper.startPage(pageNum, limit);
        List<Distribute> Network = networkDao.airForcePoolList(content,strtime,endtime);
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
    public int putAirForcePoll(Distribute distribute) {
        String trackId = Rand.getTrackId("WL");//获得跟踪单号
        distribute.setTrackId(trackId);
        return networkDao.putAirForcePoll(distribute);
    }

    @Override
    public int insertNetwoork(Distribute distribute) {
        return networkDao.insertNetwoork(distribute);
    }

    @Override
    public int excelNetwork(MultipartFile file) throws Exception {
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
        networkDao.excelNetwork(result);
        return 1;
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
        networkFollowDao.updateFolloupNetwork(networkFollow);
          return networkFollowDao.insertNetworkFollow(networkFollow);
    }
    @Override
    public int SubmitRecordingNetwork(Distribute network) {
        network.setStatus(3);
        return networkDao.SubmitRecordingNetwork(network);
    }

    @Override
    public List<RecordingVo> RecordingShowNetwork(Integer id) {
        return networkDao.RecordingShowNetwork(id);
    }

    @Override
    public int UpdateRecordingNetwork(Distribute network) {
        return networkDao.UpdateRecordingNetwork(network);
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
    public int setOverdueTime(AirForcePool airForcePool) {
        airForcePool.setOverdueTime(DateUtil.getOverTime(airForcePool.getMsec()));
        return networkDao.setOverdueTime(airForcePool);
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


}
