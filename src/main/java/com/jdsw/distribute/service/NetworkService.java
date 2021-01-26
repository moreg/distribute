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
import java.text.ParseException;
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
    int orderTaking(Distribute distribute,String username,String name);

    /**
     * 我的客户
     * @param
     * @return
     */
    PageInfo<Distribute> queryNetworkByLastName(int pageNum, int limit,String content, String strtime, String endtime,String lastFollowName,String usernmae) throws ParseException;

    /**
     * 主管待处理
     * @param pageNum
     * @param limit
     * @param content
     * @param strtime
     * @param endtime
     * @param
     * @return
     * @throws ParseException
     */
    PageInfo<Distribute> pendingNetworkList(int pageNum, int limit,String content, String strtime, String endtime,String lastFollowName) throws ParseException;
    /**
     * 空军池列表
     * @return
     */
    PageInfo<Distribute> airForcePoolList(Map map);

    /**
     * 抢单池
     * @param map
     * @return
     */
    PageInfo<Distribute> grabbingPool(Map map);

    /**
     * 跟单池
     * @param map
     * @return
     */
    PageInfo<Distribute> withPool(Map map);

    /**
     * 待处理
     * @param pageNum
     * @param limit
     * @param network
     * @param content
     * @param strtime
     * @param endtime
     * @param username
     * @return
     */
    PageInfo<Distribute> pendingPoolList(Map map);


    /**
     * 新增
     * @param
     * @return
     */
    int insertNetwoork(Map map);

    /**
     * 导入网销线索
     * @param file
     * @return
     */
    int excelNetwork(MultipartFile file,String username,String name) throws Exception;
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
     * 写跟进
     * @param networkFollow
     * @return
     */
    int followupNetwork(DistributeFollow networkFollow,String username);

    /**
    * 提交财务
     */
    int SubmitRecordingNetwork(List<Distribute> network);
    /**
     * 财务录单
     * @param network
     * @return
     */
    int UpdateRecordingNetwork(Distribute network);
    /**
     * 财务完成列表
     * @return
     */
    PageInfo<CashierVo> cashierCompleteLis(Map map);
    /**
     * 财务查询列表
     * @param
     * @return
     */
    PageInfo<CashierVo> cashierListNetwork(Map map);
    /**
     * 主管转单
     * @param distribute
     * @return
     */
    int transferNetwork(List<Distribute> distribute,String name);
    /**
     * 客服转单
     * @param distribute
     * @return
     */
    int customerTransfer(List<Distribute> distribute,String name);
    /**
     * 设置过期时间
     * @param
     * @return
     */
    int setOverdueTime(Distribute distribute);
    /**
     * 查询跟进列表
     * @return
     */
    List<DistributeFollow> qureyFollowList(Integer id);

    /**
     * 退单
     * @param distribute
     * @return
     */
    int returnPool(Distribute distribute);
    /**
     * 退回给业务员
     * @param distribute
     * @return
     */
    int chargeback(Distribute distribute);

    /**
     * 通过
     * @param distribute
     * @return
     */
    int adopt(Distribute distribute);
    /**
     * 跟单状态
     * @param pageNum
     * @param limit
     * @param status
     * @param name
     * @return
     */
    PageInfo<Distribute> statusList(int pageNum, int limit, Integer status,String name);
    /**
     * 主动设置订单超时
     * @param distribute
     * @return
     */
    int setOvertime(Distribute distribute);

}
