package com.jdsw.distribute.dao;

import com.jdsw.distribute.model.Branch;
import com.jdsw.distribute.model.Menu;

import java.util.List;

public interface MenuDao {
     List<Menu> findTree(String usernmae);
     List<Menu> findBranch();
     List<Menu> getMenuList();
}
