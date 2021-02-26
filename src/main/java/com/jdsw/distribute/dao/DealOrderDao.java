package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.DealOrder;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.vo.CashierVo;

import java.util.List;
import java.util.Map;

public interface DealOrderDao {

    List qureyOrder(List list);

    int insertOrder(DealOrder dealOrder);

    int updateDealOrder(Distribute distribute);

    CashierVo qureyOrderAll(Distribute distribute);
}
