package com.jdsw.distribute;

import com.jdsw.distribute.dao.CustomerDao;
import com.jdsw.distribute.model.Distribute;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Customer {
    @Autowired
    private CustomerDao customerDao;
    @Test
    public void insert(){
        Distribute distribute  = new Distribute();
        distribute.setCorporateName("张三");
        distribute.setTrackId("XK202100009");
        distribute.setCorporatePhone("17777107646");
        int i = customerDao.insertCustomer(distribute);
        System.out.println(i);
    }

    @Test
    public void update(){
        Distribute distribute  = new Distribute();
        distribute.setTrackId("XK202100009");
        distribute.setCorporateName("张三");
        int i = customerDao.updateCustomer(distribute);
        System.out.println(i);
    }
}
