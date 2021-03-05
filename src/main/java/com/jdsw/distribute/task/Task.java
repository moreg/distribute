package com.jdsw.distribute.task;

import com.jdsw.distribute.dao.*;
import com.jdsw.distribute.enums.Department;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@EnableAsync
public class Task {
    @Autowired
    private NetworkDao networkDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TelemarkeDao telemarkeDao;
    @Autowired
    private NetworkFollowDao networkFollowDao;
    @Autowired
    private TelemarkeFollowDao telemarkeFollowDao;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //空军定时器
    @Async
    @Scheduled(cron = "0 0/1 * * * ? ")
   public void setOvertime() throws Exception {
        List<Distribute> distribute = networkDao.queryOverTime();
        Date now = new Date();
        Distribute distribute1 = null;
        if (distribute.size() > 0){
            for (int i=0;i<distribute.size();i++){
                if (StringUtils.isNotEmpty(distribute.get(i).getOverdueTime())){
                    distribute1 = new Distribute();
                    Date start = sdf.parse(distribute.get(i).getOverdueTime());
                    if (start.getTime() - now.getTime() > 0 && start.getTime() - now.getTime() <=300000){
                        //System.out.println("提醒即将超时");
                    }else if (start.getTime() - now.getTime() <= -600000 && start.getTime() - now.getTime() >= -1200000){
                        //System.out.println("超时十分钟提醒");
                    }else if (start.getTime() - now.getTime() <= -1200000 && start.getTime() - now.getTime() >= -1500000){
                        //System.out.println("超时二十分钟提醒");
                    }else if (start.getTime() - now.getTime() <= -1500000){
                        String role = userDao.findRoleByUserName4(distribute.get(i).getLastFollowName());
                        if (Department.SALESMAN.value.equals(role)){
                            distribute1.setStatus(5);
                            distribute1.setIssue(3);
                            distribute1.setFlag(3);
                            distribute1.setLastFollowName(distribute.get(i).getLeaderName());
                        }else if (Department.CHARGE.value.equals(role)){
                            distribute1.setStatus(5);
                            distribute1.setIssue(3);
                            distribute1.setFlag(3);
                            distribute1.setLastFollowName("聂帅");
                        }
                        distribute1.setId(distribute.get(i).getId());
                        distribute1.setOverdueTime(null);
                        distribute1.setOverrun(1);
                        networkDao.overTime(distribute1);
                        DistributeFollow networkFollow= new DistributeFollow();
                        networkFollow.setFollowName(distribute.get(i).getLastFollowName());
                        networkFollow.setNetworkId(distribute.get(i).getId());
                        networkFollow.setOperation("超时退回");
                        networkFollowDao.insertNetworkFollow(networkFollow);
                    }
                }
            }
        }
    }


    //陆军定时器
    @Async
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void Notactivation() throws  Exception{
        List<Distribute> distribute = telemarkeDao.queryOverTime();
        Distribute distribute1 = null;
        Date now = new Date();
        if (distribute.size() > 0){
            for (int i=0;i<distribute.size();i++){
                distribute1 = new Distribute();
                if (StringUtils.isNotEmpty(distribute.get(i).getOverdueTime())){
                    Date start = sdf.parse(distribute.get(i).getOverdueTime());
                    if (start.getTime() - now.getTime() < 0){
                        if (distribute.get(i).getActivation() == null){
                            return;
                        }
                        distribute1.setId(distribute.get(i).getId());
                        if (distribute.get(i).getActivation() == 1){//已激活
                            distribute1.setLastFollowName(null);
                            distribute1.setStatus(0);
                            distribute1.setOverrun(1);
                            distribute1.setOverdueTime(null);
                            telemarkeDao.overTime(distribute1);
                        }else if (distribute.get(i).getActivation() == 0){//未激活
                            if (distribute.get(i).getLeaderSign() == 1){
                                distribute1.setLastFollowName(null);
                                distribute1.setStatus(0);
                                distribute1.setOverrun(1);
                                distribute1.setOverdueTime(null);
                                telemarkeDao.overTime(distribute1);
                            }else if (distribute.get(i).getLeaderSign() == 0){//退回给主管
                                distribute1.setStatus(5);
                                distribute1.setOverrun(1);
                                distribute1.setOverdueTime(null);
                                telemarkeDao.overTime(distribute1);
                            }
                        }
                        String str = distribute.get(i).getLastFollowName()+"超时退回";
                        DistributeFollow networkFollow= new DistributeFollow();
                        networkFollow.setFollowName(distribute.get(i).getLastFollowName());
                        networkFollow.setNetworkId(distribute.get(i).getId());
                        networkFollow.setFollowResult(str);
                        telemarkeFollowDao.insertNetworkFollow(networkFollow);
                    }

                }


            }
        }
    }
}
