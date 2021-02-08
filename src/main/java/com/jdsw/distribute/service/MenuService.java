package com.jdsw.distribute.service;

import com.jdsw.distribute.model.Branch;
import com.jdsw.distribute.model.Menu;
import com.jdsw.distribute.model.SysMune;
import com.jdsw.distribute.vo.MenuVo;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface MenuService {
    /*
     * 排序,根据order排序
     */
    public Comparator<Menu> order();
    public List<SysMune> findTree(String username);
    List<Menu> findBranch();
    List<Menu> getSection(String label);
    public List<Menu> getChild(String id, List<Menu> allMenu);

    /**
     * 主菜单
     * @param username
     * @return
     */
    public List<SysMune> getMenuLsit(String username);
    List getSubordinate(Map map);

    /**
     * 下属客户菜单
     * @param map
     * @return
     */
    List getsubordinateMenuList(Map map);

    /**
     * 空军来源
     * @return
     */
    List<MenuVo> kSourceMenu(String type);
}
