package com.jdsw.distribute.service;

import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.model.Enterprise;
import com.jdsw.distribute.model.Role;
import com.jdsw.distribute.vo.AirForcePool;
import com.jdsw.distribute.vo.CashierVo;
import com.jdsw.distribute.vo.InsertVo;
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
    int orderTaking(Distribute distribute,String username,String name,String role);

    /**
     * 我的客户
     * @param
     * @return
     */
    PageInfo<Distribute> queryNetworkByLastName(Map map) throws ParseException;

    /**
     * 企业池
     * @param map
     * @return
     */
    PageInfo<Enterprise> enterprisePoolList (Map map);
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
     * @param
     * @return
     */
    int updateNetwork(Distribute distribute);

    Distribute updateNetworkPop(Integer id);

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
    int submitRecordingNetwork(List<Distribute> network);
    /**
     * 财务录单
     * @param
     * @return
     */
    int updateRecordingNetwork(Map map);
    /**
     * 财务完成列表
     * @return
     */
    PageInfo<CashierVo> cashierCompleteLis(Map map);

    /**
     * 录入弹窗
     * @param distribute
     * @return
     */
    CashierVo recordingPop(Distribute distribute);
    /**
     * 完成订单
     * @param
     * @return
     */
    PageInfo<Distribute> dealListNetwork(Map map);

    /**
     * 下属客户列表
     * @param map
     * @return
     */
    PageInfo<Distribute> subordinateList(Map map);
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
    List<DistributeFollow> qureyFollowList(Integer id,String trackId);

    /**
     * 客户信息
     * @param id
     * @param trackId
     * @return
     */
    Distribute qureyCustomer(Integer id,String trackId);
    /**
     * 客户信息
     * @param id
     * @param
     * @return
     */
    Distribute qureyCustomer2(Integer id,String customerNo);
    /**
     * 关联企业
     * @param
     * @param
     * @return
     */
    List enterpriseList(Map map);

    /**
     *
     * @param map
     * @return
     */
    int addEnterprise(Map map);
    /**
     * 办理业务列表
     * @param
     * @param
     * @return
     */
    List business(Map map);

    /**
     * 增加办理业务
     * @param map
     * @return
     */
    int addBusiness(Map map);
    /**
     * 退单
     * @param
     * @return
     */
    int returnPool(Map map);
    /**
     * 退回给业务员
     * @param
     * @return
     */
    int chargeback(Map map);

    /**
     * 驳回停止跟进
     * @param map
     * @return
     */
    //int chargebackStop(Map map);
    /**
     * 通过
     * @param
     * @return
     */
    int adopt(Map map);

    /**
     * 修改客户资料
     * @param distribute
     * @return
     */
    int updateCustomer(Distribute distribute);
    /**
     * 通过停止申请
     * @param map
     * @return
     */
    //int adoptStop(Map map);
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

    /**
     * 激活客户
     * @param map
     * @return
     */
    int activation(Map map);

    /**
     * 确认
     * @param map
     * @return
     */
    int confirm(Map map);

    /**
     * 查询是否有重复单号
     * @param trackId
     * @return
     */
    Distribute qureyTrackId(String trackId);

    /**
     * 新建上传图片
     * @param img
     * @return
     */
    Map uploadImgNew(MultipartFile[] img);

    /**
     * 停止跟进
     * @param map
     * @return
     */
    int applyStop(Map map);
}
