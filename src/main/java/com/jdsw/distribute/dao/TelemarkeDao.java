package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.vo.CashierVo;
import com.jdsw.distribute.vo.RecordingVo;

import java.util.List;
import java.util.Map;

public interface TelemarkeDao {
    /**
     * 陆军池列表
     * @return
     */
    List<Distribute> armyListPoolList(Map map);

    /**
     * 抢单池
     * @param map
     * @return
     */
    List<Distribute> grabbingPool(Map map);
    /**
     * 新增
     * @param distribute
     * @return
     */
    int insertTelemarke(Distribute distribute);
    /**
     * 删除
     * @param
     * @return
     */
    int deleteTelemarke(Distribute distribute);

    /**
     * 编辑
     * @param distribute
     * @return
     */
    int updateTelemarke(Distribute distribute);
    /**
     * 导入电销
     * @param excels
     * @return
     */
    int excelTelemarke(List<Object> excels);

    /**
     * 指定接单人
     * @param distribute
     * @return
     */
    int appoint(List<Distribute> distribute);
    /**
     *修改网络库跟踪人
     * @param distribute
     * @return
     */
    int updateNetworkLastFollowName (Distribute distribute);

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

    Distribute selectNetworkById2(Integer id);

    /**
     * 查询我的客户
     * @param
     * @return
     */
    List<Distribute> queryTelemarkeByLastName(Map map);

    List<Distribute> queryTelemarkeByLastName2(Map map);

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
    int SubmitRecordingNetwork(List<Distribute> network);

    /**
     * 未完成
     * @return
     */
    List<Distribute> Unsettled();

    /**
     * 插入财务表
     * @param distribute
     * @return
     */
    int insertDealOrder(Distribute distribute);
    /**
     * 修改激活时间
     * @param distribute
     * @return
     */
    int updateworkOverdueTime(Distribute distribute);
    /**
     * 主管分配
     * @param distribute
     * @return
     */
    int assign(List<Distribute> distribute);
    /**
     * 设置订单超时
     * @param distribute
     * @return
     */
    int setOvertime(Distribute distribute);
    /**
     * 查询是否主管分配
     * @param id
     * @return
     */
    int querySign(String id);
    /**
     * 状态查询
     * @param
     * @return
     */
    List<Distribute> statusList(Distribute distribute);
    /**
     * 查询超时时间
     * @return
     */
    List<Distribute> queryOverTime();

    /**
     * 完成订单修改状态
     * @param distribute
     * @return
     */
    int updateBytrackId(Distribute distribute);

    Distribute selectNetworkById3(String trackId);

    int updateBytrackId2(Distribute distribute);
    /**
     * 主管跟单池
     * @param map
     * @return
     */
    List<Distribute> withPool2(Map map);
    /**
     * 业务员跟单池
     * @param map
     * @return
     */
    List<Distribute> withPool(Map map);


}
