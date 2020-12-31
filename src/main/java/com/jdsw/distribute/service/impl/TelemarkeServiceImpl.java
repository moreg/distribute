package com.jdsw.distribute.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.jdsw.distribute.dao.TelemarkeDao;
import com.jdsw.distribute.dao.TelemarkeFollowDao;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.service.TelemarkeService;
import com.jdsw.distribute.util.DateUtil;
import com.jdsw.distribute.util.FileUtil;
import com.jdsw.distribute.util.Rand;
import com.jdsw.distribute.vo.CashierVo;
import com.jdsw.distribute.vo.RecordingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class TelemarkeServiceImpl implements TelemarkeService {
    @Autowired
    private TelemarkeDao telemarkDao;
    @Autowired
    private TelemarkeFollowDao telemarkeFollowDao;
    @Override
    public PageInfo<Distribute> armyListPoolList(int pageNum, int limit, String content, String strtime, String endtime) {
        PageHelper.startPage(pageNum, limit);
        List<Distribute> Network = telemarkDao.armyListPoolList(content,strtime,endtime);
        PageInfo result = new PageInfo(Network);
        return result;
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
    public int appoint(Distribute network, String name) {
        DistributeFollow networkFollow = new DistributeFollow();
        String trackId = Rand.getTrackId("DX");//获得跟踪单号
        network.setTrackId(trackId);
        String leader = network.getLeaderName();
        String department =network.getDepartment();
        if (StringUtil.isNotEmpty(leader)) {
            network.setIssue(0);
            network.setLeaderName(leader);
        }else if (StringUtil.isNotEmpty(department)){
            network.setIssue(1);
            network.setDepartment(department);
        }else {
            network.setIssue(1);
            networkFollow.setFollowName(name);
            networkFollow.setNetworkId(network.getId());
            networkFollow.setFollowResult("给你转交了一条线索");
            telemarkeFollowDao.insertNetworkFollow(networkFollow);//插入跟进日志
        }
        network.setAppoint(1);
        String date = DateUtil.getNextDay();
        network.setOverdueTime(date);
        network.setActivation(0);
        telemarkDao.updateworkOverdueTime(network);//修改激活时间
        telemarkDao.updateNetworkTrackId(network);//生成跟踪单号
        return telemarkDao.appoint(network);
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
    public int putarmyPoll(Distribute distribute) {
        String trackId = Rand.getTrackId("DX");//获得跟踪单号
        distribute.setTrackId(trackId);
        return telemarkDao.putarmyPoll(distribute);
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
        distribute.setOverdueTime(DateUtil.getSanDay());
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

        }
       // telemarkDao.updateRetuen(distribute);
        telemarkDao.updateworkOverdueTime(distribute);//修改激活时间为3天后
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
    public int SubmitRecordingNetwork(Distribute network) {
        network.setStatus(3);
        return telemarkDao.SubmitRecordingNetwork(network);
    }

    @Override
    public List<RecordingVo> RecordingShowNetwork(Integer id) {
        return telemarkDao.RecordingShowNetwork(id);
    }

    @Override
    public int UpdateRecordingNetwork(Distribute network) {
        return telemarkDao.UpdateRecordingNetwork(network);
    }

    @Override
    public PageInfo<CashierVo> cashierListNetwork(int pageNum, int limit, String content, String strtime, String endtime) {
        PageHelper.startPage(pageNum, limit);
        List<CashierVo> CashierVo = telemarkDao.cashierListNetwork(content,strtime,endtime);
        PageInfo result = new PageInfo(CashierVo);
        return result;

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
    public int assign(Distribute distribute) {
        distribute.setLeaderSign(1);
        return telemarkDao.assign(distribute);
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
}
