package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.vo.CashierVo;
import com.jdsw.distribute.vo.RecordingVo;

import java.util.List;

public interface TelemarkeDao {
    /**
     * 陆军池列表
     * @param content
     * @param strtime
     * @param endtime
     * @return
     */
    List<Distribute> armyListPoolList(String content, String strtime, String endtime);

    /**
     * 抢单列表
     * @param content
     * @param strtime
     * @param endtime
     * @return
     */
    List<Distribute> grabbingOrdersList(String content, String strtime, String endtime);

    /**
     * 指定接单人
     * @param distribute
     * @return
     */
    int appoint(Distribute distribute);

    /**
     * 生成跟踪单号
     * @param distribute
     * @return
     */
    int updateNetworkTrackId(Distribute distribute);



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

    /**
     * 查询我的订单
     * @param
     * @return
     */
    List<Distribute> queryTelemarkeByLastName(String content, String strtime, String endtime,String lastFollowName);

    /**
     * 超时
     * @param network
     * @return
     */
    int overTime(Distribute network);

    /**
     * 放客户线索到陆军池
     * @param distribute
     * @return
     */
    int putarmyPoll(Distribute distribute);

    /**
     * 提交财务
     * @param network
     * @return
     */
    int SubmitRecordingNetwork(Distribute network);

    /**
     * 录单弹窗信息
     * @param id
     * @return
     */
    List<RecordingVo> RecordingShowNetwork(Integer id);
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
    List<CashierVo> cashierListNetwork(String content, String strtime, String endtime);

    /**
     * 未完成
     * @return
     */
    List<Distribute> Unsettled();

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
    int assign(Distribute distribute);
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
    int querySign(Integer id);
}
