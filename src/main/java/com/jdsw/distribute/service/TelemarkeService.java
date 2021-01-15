package com.jdsw.distribute.service;

import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.vo.CashierVo;
import com.jdsw.distribute.vo.RecordingVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface TelemarkeService {
    /**
     * 陆军池列表
     * @param pageNum
     * @param limit
     * @param content
     * @param strtime
     * @param endtime
     * @return
     */
    PageInfo<Distribute> armyListPoolList(int pageNum, int limit, String content, String strtime, String endtime,String username);
    /**
     * 新增
     * @param distribute
     * @return
     */
    int insertTelemarke(Distribute distribute,String username);
    /**
     * 导入电销线索
     * @param file
     * @return
     */
    int excelTelemarke(MultipartFile file,String username) throws Exception;
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
     * 编辑查询客户信息
     * @param id
     * @return
     */
    List<Map> qureyTelemarke(Integer id);
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
     * 指定接单人
     * @param network
     * @param name
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
    PageInfo<Distribute> queryTelemarkeByLastName(int pageNum, int limit,String content, String strtime, String endtime,String lastFollowName);

    /**
     * 我的客户待处理
     * @param pageNum
     * @param limit
     * @param content
     * @param strtime
     * @param endtime
     * @param lastFollowName
     * @return
     * @throws ParseException
     */
    PageInfo<Distribute> pendingNetworkList(int pageNum, int limit,String content, String strtime, String endtime,String lastFollowName) throws ParseException;
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
     * 上传图片
     * @param img
     * @return
     */
    int uploadImg(MultipartFile img, HttpServletRequest request);
    /**
     * 提交财务
     */
    int SubmitRecordingNetwork(List<Distribute> network);


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
    int transferNetwork(Distribute distribute);
    /**
     * 主管分配订单
     * @param distribute
     * @return
     */
    int assign(List<Distribute> distribute,String username);
    /**
     * 客服分配
     * @param distribute
     * @return
     */
    int customerTransfer(List<Distribute> distribute,String username);
    /**
     * 查询跟进列表
     * @return
     */
    List<DistributeFollow> qureyFollowList(Integer id);

    /**
     * 设置订单超时
     * @param distribute
     * @return
     */
    int setOvertime(Distribute distribute);

    PageInfo<Distribute> statusList(int pageNum, int limit, Integer status,String name);
}
