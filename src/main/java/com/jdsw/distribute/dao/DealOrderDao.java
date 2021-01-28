package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.DealOrder;

import java.util.List;
import java.util.Map;

public interface DealOrderDao {

    List qureyOrder(List list);

    int insertOrder(DealOrder dealOrder);
}
