package com.jdsw.distribute.service;

import com.jdsw.distribute.model.Branch;
import com.jdsw.distribute.model.Menu;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface MenuService {
    /*
     * 排序,根据order排序
     */
    public Comparator<Menu> order();
    public List<Menu> findTree(String username);
    List<Menu> findBranch();
    List<Menu> getSection(String label);
    public List<Menu> getChild(String id, List<Menu> allMenu);
    public List<Menu> getMenuLsit();
    List getSubordinate(Map map);
    List getsubordinateMenuList(Map map);
}
