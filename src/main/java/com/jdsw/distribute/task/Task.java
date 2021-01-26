package com.jdsw.distribute.task;

import com.jdsw.distribute.dao.NetworkDao;
import com.jdsw.distribute.dao.TelemarkeDao;
import com.jdsw.distribute.dao.UserDao;
import com.jdsw.distribute.model.Distribute;
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

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //一分钟执行一次
   /* @Async
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void setOvertime() throws Exception {
        List<Distribute> distribute = networkDao.queryOverTime();
        Date now = new Date();
        Distribute distribute1 = null;
        for (int i=0;i<distribute.size();i++){
            distribute1 = new Distribute();
            if (StringUtils.isNotEmpty(DateUtil.parseDate()distribute.get(i).getOverdueTime())){

                Date start = sdf.parse(distribute.get(i).getOverdueTime());
                if (start.getTime() - now.getTime() > 0 && start.getTime() - now.getTime() <=300000){
                    distribute1.setId(distribute.get(i).getId());
                    distribute1.setStatus(6);
                    networkDao.SubmitRecordingNetwork(distribute1);
                    System.out.println(distribute.get(i).getId());
                    System.out.println("提醒即将超时");
                }else if (start.getTime() - now.getTime() <= -600000 && start.getTime() - now.getTime() >= -1200000){
                    System.out.println(distribute.get(i).getId());
                    System.out.println("超时十分钟提醒");
                }else if (start.getTime() - now.getTime() <= -1200000 && start.getTime() - now.getTime() >= -1500000){
                    System.out.println(distribute.get(i).getId());
                    System.out.println("超时二十分钟提醒");
                }else if (start.getTime() - now.getTime() <= -1500000){
                    System.out.println(distribute.get(i).getId());
                    distribute1.setId(distribute.get(i).getId());
                    distribute1.setStatus(9);
                    networkDao.SubmitRecordingNetwork(distribute1);
                    System.out.println("超时返回主管");
                }
            }
        }
    }
    @Async
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void Notactivation() throws  Exception{
        List<Distribute> distribute = telemarkeDao.queryOverTime();
        Distribute distribute1 = null;
        Date now = new Date();
        for (int i=0;i<distribute.size();i++){
            distribute1 = new Distribute();
            if (StringUtils.isNotEmpty(distribute.get(i).getOverdueTime())){
                Date start = sdf.parse(distribute.get(i).getOverdueTime());
                if (start.getTime() - now.getTime() < 0){
                    if (distribute.get(i).getActivation() == null){
                        return;
                    }
                    if (distribute.get(i).getActivation() == 1){//已激活
                        distribute1.setLastFollowName(null);
                        distribute1.setStatus(2);
                        telemarkeDao.overTime(distribute1);
                    }else if (distribute.get(i).getActivation() == 0){//未激活
                        if (distribute.get(i).getLeaderSign() == 1){
                            distribute1.setLastFollowName(null);
                            distribute1.setStatus(2);
                            telemarkeDao.overTime(distribute1);
                        }else if (distribute.get(i).getLeaderSign() == 0){
                            distribute1.setStatus(9);
                            telemarkeDao.overTime(distribute1);
                        }
                    }
                }
            }
        }

    }*/
}
