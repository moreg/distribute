package com.jdsw.distribute;

import com.jdsw.distribute.dao.NetworkDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributeTest {
    @Autowired
    private NetworkDao networkDao;

    @Test
    public void list(){
       // networkDao.
    }


}
