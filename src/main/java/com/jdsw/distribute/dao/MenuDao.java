package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.Branch;
import com.jdsw.distribute.model.Menu;
import com.jdsw.distribute.model.SysMune;
import com.jdsw.distribute.vo.MenuVo;

import java.util.List;

/**
 * 菜单
 */
public interface MenuDao {
     List<SysMune> findTree(String usernmae);
     List<Menu> findBranch();
     List<SysMune> getMenuList(String username);

     List<Menu> businessMenu();
     
     List<MenuVo> kSourceMenu();

     List<MenuVo> lSourceMenu();
}
