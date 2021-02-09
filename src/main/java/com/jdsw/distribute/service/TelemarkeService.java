package com.jdsw.distribute.service;

import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.vo.CashierVo;
import com.jdsw.distribute.vo.InsertVo;
import com.jdsw.distribute.vo.RecordingVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface TelemarkeService {
    /**
     * 陆军池列表
     * @return
     */
    PageInfo<Distribute> armyListPoolList(Map map);

    /**
     * 抢单池
     * @param map
     * @return
     */
    PageInfo<Distribute> grabbingPool(Map map);
    /**
     * 新增
     * @param
     * @return
     */
    int insertTelemarke(Distribute distribute, String username, String name);
    /**
     * 导入电销线索
     * @param file
     * @return
     */
    int excelTelemarke(MultipartFile file,String username,String name) throws Exception;
    /**
     * 删除
     * @param
     * @return
     */
    int deleteTelemarke(Distribute distribute);

    /**
     * 编辑
     * @param
     * @return
     */
    int updateTelemarke(Distribute distribute);

    Distribute updateTelemarkePop(Integer id);

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
    int orderTaking(Distribute distribute,String name,String role);

    /**
     * 我的客户
     * @param
     * @return
     */
    PageInfo<Distribute> queryTelemarkeByLastName(Map map);

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
    int assign(List<Distribute> distribute,String username,String name);
    /**
     * 客服分配
     * @param distribute
     * @return
     */
    int customerTransfer(List<Distribute> distribute,String username,String name);
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
