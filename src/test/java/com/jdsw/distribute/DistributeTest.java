package com.jdsw.distribute;

import com.jdsw.distribute.dao.NetworkDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributeTest {
    @Autowired
    private NetworkDao networkDao;

    @Test
    public void list(){
       // networkDao.
    }
    @Test
    public void get(){
        Map map = new HashMap();
        map.put("lastFollowName","123");
        System.out.println(networkDao.selectDeliver(map));
    }

}
