package com.jdsw.distribute.task;

import com.jdsw.distribute.dao.NetworkDao;
import com.jdsw.distribute.model.Distribute;
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
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //每隔2秒执行一次
    /*@Scheduled(fixedRate = 2000)
    public void testTasks() {
        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
    }*/

    //每天3：05执行
    @Async
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void setOvertime() throws Exception {
        List<Distribute> distribute = networkDao.queryOverTime();
        Date now = new Date();

       /* for (int i=0;i<distribute.size();i++){
            if (StringUtils.isNotEmpty(distribute.get(i).getOverdueTime())){

                Date start = sdf.parse(distribute.get(i).getOverdueTime());
                if (start.getTime() - now.getTime() > 0 && start.getTime() - now.getTime() <=300000){
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
                    System.out.println("超时返回主管");
                }
            }
        }*/
    }
}
