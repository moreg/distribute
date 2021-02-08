package com.jdsw.distribute;

import com.jdsw.distribute.dao.EnterpriseDao;
import com.jdsw.distribute.model.Enterprise;
import com.jdsw.distribute.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EnterpriseTest {
    @Autowired
    UserService us;
    @Autowired
    private EnterpriseDao enterpriseDao;
    @Test
    public void insert(){
        Enterprise enterprise = new Enterprise();
        //enterprise.setSource(2);
        enterprise.setTrackId("KZ12345679");
        enterprise.setCorporatePhone("17620116556");
        enterprise.setCorporateName("叶问");
        enterprise.setAddName("陈真");
        enterprise.setAddress("浩天广场");
        enterprise.setEstablishTime("2020-12-06");
        enterprise.setName("广西恒带");
        enterprise.setRegisteredCapital("100W");
        enterprise.setRelation("股东");
        enterprise.setCorporatePhone2("17777104656");
        enterprise.setCorporatePhone3("17777102254");
        System.out.println(enterpriseDao.insertEnterprise(enterprise));
    }

    @Test
    public void list(){
       // System.out.println(enterpriseDao.enterpriseList("17620116556",null,""));

    }
}
