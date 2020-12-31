package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.DistributeFollow;

public interface NetworkFollowDao {
    /**
     * 插入跟进日志
     * @param
     * @return
     */
    int insertNetworkFollow(DistributeFollow networkFollow);
    /**
     * 写跟进修改客户状态
     * @param networkFollow
     * @return
     */
    int updateFolloupNetwork(DistributeFollow networkFollow);
}
