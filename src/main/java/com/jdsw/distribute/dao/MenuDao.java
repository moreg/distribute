package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.Branch;
import com.jdsw.distribute.model.Menu;
import com.jdsw.distribute.model.SysMune;

import java.util.List;

public interface MenuDao {
     List<SysMune> findTree(String usernmae);
     List<Menu> findBranch();
     List<SysMune> getMenuList(String username);
}
