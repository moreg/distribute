package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.Business;
import com.jdsw.distribute.model.DealOrder;

import java.util.List;

public interface BusinessDao {
    /**
     * 添加业务
     * @param list
     * @return
     */
    int insertBusiness(List list);

    /**
     * 查询业务
     * @param list
     * @return
     */
    List qureyBusiness(List list);

    int insertBusiness2(DealOrder order);
}
