package com.jdsw.distribute.service.impl;

import com.jdsw.distribute.dao.EnterpriseDao;
import com.jdsw.distribute.model.Enterprise;
import com.jdsw.distribute.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;

public class EnterpriseServiceImpl implements EnterpriseService {
    @Autowired
    private EnterpriseDao enterpriseDao;
    @Override
    public int insertEnterprise(Enterprise enterprise) {
        return enterpriseDao.insertEnterprise(enterprise);
    }
}
