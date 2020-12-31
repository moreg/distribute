package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;

public interface TelemarkeFollowDao {
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

    /**
     * 查询是否有跟进记录
     * @param distribute
     * @return
     */
    String qureyFollowByLastName(Distribute distribute);
}
