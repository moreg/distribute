package com.jdsw.distribute.service;

import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.vo.CashierVo;
import com.jdsw.distribute.vo.RecordingVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    PageInfo<Distribute> armyListPoolList(int pageNum, int limit, String content, String strtime, String endtime);

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
    int appoint(Distribute network, String name);
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
     * 放客户线索到陆军池
     */
    int putarmyPoll(Distribute distribute);
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
    int transferNetwork(Distribute distribute);
    /**
     * 主管分配订单
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
}
