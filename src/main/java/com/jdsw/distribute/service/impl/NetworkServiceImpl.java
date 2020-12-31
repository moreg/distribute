package com.jdsw.distribute.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.dao.NetworkDao;
import com.jdsw.distribute.dao.NetworkFollowDao;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.model.Role;
import com.jdsw.distribute.service.NetworkService;
import com.jdsw.distribute.util.*;
import com.github.pagehelper.util.StringUtil;

import com.jdsw.distribute.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.*;



@Service
public class NetworkServiceImpl implements NetworkService {

    @Autowired
    private NetworkDao networkDao;
    @Autowired
    private NetworkFollowDao networkFollowDao;
    @Override
    public int insertTelemarkting(Distribute distribute) {
        return 0;
    }

    @Override
    public int ExclDistribute(MultipartFile file) throws Exception {
        Map map = new HashMap();
        InputStream is = null;
        String filePath = "E:\\upexl\\";
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
            String newFileName = file.getOriginalFilename();
            File newFile = new File(filePath + newFileName);
            //复制操作
            file.transferTo(newFile);
            //读取保存的文件
            is = new FileInputStream((filePath + newFileName));
            List<Distribute> list = (List<Distribute>) ExcelUtil.getBankListByExcel(is);
            int insert = networkDao.insertTelemarkting(list);
            if (insert > 0) {
                return 1;
        }
        return 0;
    }
    @Override
    @Transactional
    public int appoint(List<Distribute> network, String name) {
        for(int i=0;i<network.size();i++){
            DistributeFollow networkFollow = new DistributeFollow();
            String trackId = Rand.getTrackId("WL");//获得跟踪单号
            network.get(i).setTrackId(trackId);
            String leader = network.get(i).getLeaderName();
            String department =network.get(i).getDepartment();
            if (StringUtil.isNotEmpty(leader)) {
                network.get(i).setIssue(0);
                network.get(i).setLeaderName(leader);
            }else if (StringUtil.isNotEmpty(department)){
                network.get(i).setIssue(1);
                network.get(i).setDepartment(department);
            }else {
                network.get(i).setIssue(1);
                networkFollow.setFollowName(name);
                networkFollow.setNetworkId(network.get(i).getId());
                networkFollow.setFollowResult("给你转交了一条线索");
                networkFollowDao.insertNetworkFollow(networkFollow);
            }
            network.get(i).setAppoint(1);
        }

        //networkDao.updateNetworkTrackId(network);
        return networkDao.appoint(network);
    }
    @Override
    public int orderTaking(Distribute network) {
        Distribute network2 = networkDao.selectNetworkById(network.getId());
        Integer status = network2.getStatus();
        if (status == 1 || status == 2){
            int o = networkDao.updateNetworkLastFollowName(network);
            if (o > 0){
                return 2;
            }
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
    public int transferNetwork(Distribute distribute) {
        return networkDao.updateNetworkLastFollowName(distribute);
    }

    @Override
    public int setOverdueTime(AirForcePool airForcePool) {
        airForcePool.setOverdueTime(DateUtil.getOverTime(airForcePool.getMsec()));
        return networkDao.setOverdueTime(airForcePool);
    }


}
