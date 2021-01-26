package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.Distribute;

import java.util.List;

public interface DevelopDao {
    List DevelopList(Distribute distribute);
    int insertDevelop(Distribute distribute);

}
