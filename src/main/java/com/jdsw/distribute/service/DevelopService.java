package com.jdsw.distribute.service;

import com.jdsw.distribute.model.Distribute;

import java.util.List;
import java.util.Map;

public interface DevelopService {
    /**
     * 自开发列表
     * @param map
     * @return
     */
    List<Distribute> DevelopList(Map map);

    /**
     * 自开发新增
     * @param
     * @return
     */
    int insertDevelop(Map map);
}
