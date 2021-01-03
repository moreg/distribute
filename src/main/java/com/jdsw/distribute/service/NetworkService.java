package com.jdsw.distribute.service;

import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.model.Role;
import com.jdsw.distribute.vo.AirForcePool;
import com.jdsw.distribute.vo.CashierVo;
import com.jdsw.distribute.vo.RecordingVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface NetworkService {
    /**
     * 指定接单人
     * @param
     * @return
     */
    int appoint(List<Distribute> network, String name);

    /**
     * 接单
     * @param distribute
     * @return
     */
    int orderTaking(Distribute distribute);

    /**
     * 我的客户
     * @param
     * @return
     */
    PageInfo<Distribute> queryNetworkByLastName(int pageNum, int limit,String content, String strtime, String endtime,String lastFollowName);


    /**
     * 空军池列表
     * @return
     */
    PageInfo<Distribute> airForcePoolList(int pageNum, int limit, Distribute network, String content, String strtime, String endtime);
    /**
     * 抢单列表
     * @param pageNum
     * @param limit
     * @param content
     * @param strtime
     * @param endtime
     * @return
     */
    PageInfo<Distribute> grabbingOrdersList(int pageNum, int limit, String content, String strtime, String endtime);
    /**
     * 放客户线索到空军池
     */
    int putAirForcePoll(Distribute distribute);

    /**
     * 新增
     * @param distribute
     * @return
     */
    int insertNetwoork(Distribute distribute);

    /**
     * 导入网销线索
     * @param file
     * @return
     */
    int excelNetwork(MultipartFile file) throws Exception;
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
     * 编辑查询客户信息
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
     * 写跟进
     * @param networkFollow
     * @return
     */
    int followupNetwork(DistributeFollow networkFollow);


    /**
    * 提交财务
     */
    int SubmitRecordingNetwork(Distribute network);
    /**
     * 录单弹窗
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
    PageInfo<CashierVo> cashierListNetwork(int pageNum, int limit, String content, String strtime, String endtime);

    /**
     * 未成交
     * @param
     * @return
     */
    int Unsettled();

    /**
     * 转单
     * @param distribute
     * @return
     */
    int transferNetwork(List<Distribute> distribute);

    /**
     * 设置过期时间
     * @param
     * @return
     */
    int setOverdueTime(AirForcePool airForcePool);

    /**
     * 查询跟进列表
     * @return
     */
    List<DistributeFollow> qureyFollowList(Integer id);

    List<Distribute> notice();

}
