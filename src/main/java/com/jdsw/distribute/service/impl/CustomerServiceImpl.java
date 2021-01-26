package com.jdsw.distribute.service.impl;

import com.jdsw.distribute.dao.CustomerDao;
import com.jdsw.distribute.dao.NetworkDao;
import com.jdsw.distribute.dao.TelemarkeDao;
import com.jdsw.distribute.dao.UserDao;
import com.jdsw.distribute.model.Customer;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private NetworkDao networkDao;
    @Autowired
    private UserDao userDao;
    @Override
    @Transactional
    public int activation(Map map) {
        Set set = userDao.findRoleByUserName2((String) map.get("username"));
        Distribute distribute  = new Distribute();
        Customer customer = (Customer) map.get("customer");
        customer.setActivationName((String) map.get("name"));
        distribute.setTrackId(customer.getTrackId());
        distribute.setActivation(1);
        networkDao.updateBytrackId(distribute);
        return customerDao.insertCustomer(customer);
    }
}
