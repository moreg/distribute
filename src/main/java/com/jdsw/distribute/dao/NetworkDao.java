package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.model.Excel;
import com.jdsw.distribute.model.Role;
import com.jdsw.distribute.vo.AirForcePool;
import com.jdsw.distribute.vo.CashierVo;
import com.jdsw.distribute.vo.RecordingVo;

import java.util.List;
import java.util.Map;

public interface NetworkDao {
    /**
     * 指定接单人
     * @param distribute
     * @return
     */
    int appoint(List<Distribute> distribute);
    /**
     * 生成跟踪单号
     * @param distribute
     * @return
     */
    int updateNetworkTrackId(Distribute distribute);
    /**
     * 查询跟踪单号
     * @param distribute
     * @return
     */
    String selectNetworkTrackId(Distribute distribute);
    /**
     *修改网络库跟踪人
     * @param distribute
     * @return
     */
    int updateNetworkLastFollowName (List<Distribute> distribute);
    /**
     *修改网络库跟踪人
     * @param distribute
     * @return
     */
    int updateNetworkLastFollowName2 (List<Distribute> distribute);
    /**
     * 抢单修改第一接单人和当前接单人
     * @param network
     * @return
     */
    int updateNetworkFirstFollowName(Distribute network);
    /**
     * 跟进id查询网络库订单信息
     * @param id
     * @return
     */
    Distribute selectNetworkById(Integer id);

    /**
     * 跟进id查询网络库订单信息
     * @param
     * @return
     */
    Distribute selectNetworkById3(String trackId);

    /**
     * 抢单行锁
     * @param id
     * @return
     */
    Distribute selectNetworkById2(Integer id);

    /**
     * 查询我的客户
     * @param
     * @return
     */
    List<Distribute> queryNetworkByLastName(Map map);
    /**
     * 查询我的客户
     * @param
     * @return
     */
    List<Distribute> queryNetworkByLastName2(Map map);
    /**
     * 主管待处理
     * @param content
     * @param strtime
     * @param endtime
     * @param lastFollowName
     * @return
     */
    List<Distribute> pendingNetworkList(String content, String strtime, String endtime,String lastFollowName);

    /*
     * 空分发池列表
     */
    List<Distribute> airForcePoolList(Map map);

    /**
     * 抢单池
     * @return
     */
    List<Distribute> grabbingPool(Map map);

    /**
     * 主管跟单池
     * @param map
     * @return
     */
    List<Distribute> withPool(Map map);
    /**
     * 业务员跟单池
     * @param map
     * @return
     */
    List<Distribute> withPool2(Map map);

    /**
     * 成交订单
     * @param map
     * @return
     */
    List<Distribute> dealListNetwork(Map map);

    /**
     * 下属客户
     * @param map
     * @return
     */
    List<Distribute> subordinateList(Map map);

    /**
     * 新增
     * @param distribute
     * @return
     */
    int insertNetwoork(Distribute distribute);

    /**
     * 导入网销
     * @param excels
     * @return
     */
    int excelNetwork(List<Object> excels);
    /**
     * 删除
     * @param
     * @return
     */
    int deleteNetwork(Distribute distribute);

    /**
     * 编辑
     * @param distribute
     * @return
     */
    int updateNetwork(Distribute distribute);

    /**
     * 超时
     * @param network
     * @return
     */
    int overTime(Distribute network);
    /**
     * 提交财务
     * @param network
     * @return
     */
    int SubmitRecordingNetwork2(List<Distribute> network);

    /**
     * 财务录单
     * @param network
     * @return
     */
    int UpdateRecordingNetwork(Distribute network);
    /**
     * 财务查询列表
     * @param
     * @return
     */
    List<CashierVo> cashierListNetwork(Map map);


    /**
     * 设置过期时间
     * @param
     * @return
     */
    int setOverdueTime(Distribute distribute);

    int insertDealOrder(Distribute distribute);

    /**
     *  提交录单
     * @param distribute
     * @return
     */
    int insertDistrbuteOrder(Distribute distribute);

    /**
     * 状态查询
     * @param
     * @return
     */
    List<Distribute> statusList(Distribute distribute);

    /**
     * 完成录单修改订单状态
     * @param distribute
     * @return
     */
    int updateBytrackId(Distribute distribute);
    /**
     * 完成录单修改订单状态
     * @param distribute
     * @return
     */
    int updateBytrackId2(Distribute distribute);
    /**
     * 完成录单修改订单状态
     * @param distribute
     * @return
     */
    int updateBytrackId3(Distribute distribute);
    /**
     * 强制超时
     * @param
     * @return
     */
    int setOvertime(Distribute distribute);

    /**
     * 查询合同的跟踪单号
     * @param distribute
     * @return
     */
    String qureydealOrder(Distribute distribute);

    /**
     * 激活
     * @param distribute
     * @return
     */
    int activation(Distribute distribute);
    /**
     * 跟进修改订单
     */
    int updateNetwork2(Distribute distribute);

    List<Distribute> queryOverTime();

    /**
     * 获取行号
     * @return
     */
    Integer getRowNo(String type);

    /**
     * 修改单号计数
     * @param map
     * @return
     */
    int updateRow(Map map);

    /**
     * 新增转交记录
     * @param distribute
     * @return
     */
    int insertDeliver(List<Distribute> distribute);

    /**
     * 转交列表
     * @param
     * @return
     */
    List<Distribute> selectDeliver(Map  map);

    /**
     * 查询是否有重复号码
     * @param corporatePhone
     * @return
     */
    Distribute selectNetworkByPhone(String corporatePhone);
}
