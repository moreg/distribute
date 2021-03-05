package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.Distribute;

import java.util.List;
import java.util.Map;

public interface CustomerDao {
    /**
     * 新增
     * @param distribute
     * @return
     */
    int insertCustomer(Distribute distribute);

    /**
     * 修改
     * @param distribute
     * @return
     */
    int updateCustomer(Distribute distribute);

    /**
     * 客户池列表
     * @return
     */
    List<Distribute> customerList(Map map);

    /**
     * 查询客户信息
     * @param
     * @return
     */
    Distribute selectCustomer(String customerNo);

    /**
     * 查手机号码查询
     * @param corporatePhone
     * @return
     */
    Distribute selectCustomer2(String corporatePhone);
}
