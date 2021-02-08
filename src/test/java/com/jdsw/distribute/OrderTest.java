package com.jdsw.distribute;

import com.jdsw.distribute.dao.DealOrderDao;
import com.jdsw.distribute.model.DealOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {
    @Autowired
    private DealOrderDao dealOrder;

    @Test
    public void list(){
        String corporate_phone = "17777107646";
        String corporate_phone2 = "17620116556";
        String corporate_phone3 = "18279785144";
        List list = new ArrayList();
        list.add(corporate_phone);
        list.add(corporate_phone2);
        list.add(corporate_phone3);

        System.out.println(dealOrder.qureyOrder(list));
    }

    @Test
    public void insert(){
        BigDecimal bigDecimal = new BigDecimal(123654);
        DealOrder dealOrder2 = new DealOrder();
        dealOrder2.setName("广西富士康");
        dealOrder2.setCorporateName("大老板");
        dealOrder2.setCorporatePhone("17620116556");
        dealOrder2.setLastFollowName("陈真");
        dealOrder2.setContractNo("165465756154");
        dealOrder2.setConduct("开公司");
        //dealOrder2.setSource(3);
        System.out.println(dealOrder.insertOrder(dealOrder2));
    }
}
