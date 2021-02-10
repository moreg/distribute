package com.jdsw.distribute.service;

import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface DevelopService {

    /**
     * 自开发新增
     * @param
     * @return
     */
    int insertDevelop(Map map);

    /**
     * 自开发列表
     * @param map
     * @return
     * @throws ParseException
     */
    PageInfo<Distribute> developList(Map map);

    /**
     * 编辑
     * @param distribute
     * @return
     */
    int updateDevelop(Distribute distribute);

    /**
     * 编辑弹窗
     * @param id
     * @return
     */
    Distribute updateDevelopPop(Integer id);

    /**
     * 删除
     * @param distribute
     * @return
     */
    int deleteDevelop(Distribute distribute);

    /**
     * 跟进列表
     * @return
     */
    List<DistributeFollow> qureyFollowList(Integer id);
    /**
     * 写跟进
     * @param distributeFollow
     * @return
     */
    int followupDevelop(DistributeFollow distributeFollow);
    /**
     * 客户信息
     * @param id
     * @return
     */
    Distribute selectDeveolpById(Integer id);

    int submitRecordingNetwork(List<Distribute> list);
    /**
     * 主管转单
     * @param distribute
     * @return
     */
    int transferNetwork(List<Distribute> distribute,String name);

}
