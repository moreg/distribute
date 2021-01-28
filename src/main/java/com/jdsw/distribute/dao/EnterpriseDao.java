package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.Enterprise;

import java.util.List;
import java.util.Map;

public interface EnterpriseDao {
    /**
     * 新增企业信息
     * @param enterprise
     * @return
     */
    int insertEnterprise(Enterprise enterprise);

    /**
     * 查询企业信息
     * @param map
     * @return
     */
    List enterpriseList(Map map);

    /**
     * 企业池列表
     * @param map
     * @return
     */
    List<Enterprise> enterprisePoolList(Map map);
}
