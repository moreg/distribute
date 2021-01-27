package com.jdsw.distribute.service;

import com.github.pagehelper.PageInfo;
import com.jdsw.distribute.model.Distribute;

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
     * 删除
     * @param distribute
     * @return
     */
    int deleteDevelop(Distribute distribute);
}
