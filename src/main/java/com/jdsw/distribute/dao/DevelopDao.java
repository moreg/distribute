package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.Enterprise;

import java.util.List;
import java.util.Map;

public interface DevelopDao {
    /**
     * 新增
     * @param distribute
     * @return
     */
    int insertDevelop(Distribute distribute);

    /**
     * 列表
     * @param map
     * @return
     */
    List developList(Map map);

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

    Distribute selectDeveolpById(Integer id);
}
