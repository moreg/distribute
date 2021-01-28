package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;

import java.util.List;

public interface DevelopFollowDao {

    int insertDevelopFollow(DistributeFollow distributeFollow);

    List<DistributeFollow> qureyFollowList(Integer integer);
}
