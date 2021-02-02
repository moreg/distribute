package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;

import java.util.List;

public interface DevelopFollowDao {

    int insertDevelopFollow(DistributeFollow distributeFollow);

    List<DistributeFollow> qureyFollowList(Integer id);
    /**
     * 批量插入跟进日志
     * @param networkFollow
     * @return
     */
    int insertNetworkFollow2(List<DistributeFollow> networkFollow);

}
