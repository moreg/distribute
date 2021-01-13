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
     * 查询我的客户
     * @param
     * @return
     */
    List<Distribute> queryNetworkByLastName(String content, String strtime, String endtime,String lastFollowName);

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
     * 空军池列表
     */
    List<Distribute> airForcePoolList(String content, String strtime, String endtime);

    List<Distribute> airForcePoolList2(String content, String strtime, String endtime);

    /**
     * 客服待处理
     * @param content
     * @param strtime
     * @param endtime
     * @return
     */
    List<Distribute> pendingPoolList(String content, String strtime, String endtime);

    /**
     * 抢单列表
     * @param content
     * @param strtime
     * @param endtime
     * @return
     */
    List<Distribute> grabbingOrdersList(String content, String strtime, String endtime);
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
     * 编辑查询
     * @param id
     * @return
     */
    List<Map> qureyNetwork(Integer id);
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
    int SubmitRecordingNetwork(Distribute network);
    /**
     * 提交财务
     * @param network
     * @return
     */
    int SubmitRecordingNetwork2(List<Distribute> network);
    /**
     * 录单弹窗信息
     * @param id
     * @return
     */
    RecordingVo RecordingShowNetwork(Integer id);
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
     * 设置过期时间
     * @param
     * @return
     */
    int setOverdueTime(Distribute distribute);

    int insertDealOrder(Distribute distribute);

    /**
     *
     * @return
     */
    List<Distribute> notice();

    /**
     * 查询超时时间
     * @return
     */
    List<Distribute> queryOverTime();

    /**
     * 状态查询
     * @param
     * @return
     */
    List<Distribute> statusList(Distribute distribute);
}
