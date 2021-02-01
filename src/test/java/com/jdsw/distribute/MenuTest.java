package com.jdsw.distribute;

import com.jdsw.distribute.dao.UserDao;
import com.jdsw.distribute.service.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuTest {
    @Autowired
    MenuService menuService;
    @Autowired
    UserDao dao;
    @Test
    public void test(){
        Map map = new HashMap();
        map.put("username","zl");
        map.put("branch","梧州分公司");
        System.out.println(menuService.getsubordinateMenuList(map));

    }
    @Test
    public void rolename(){
        dao.findRoleByUserName3("zl");
    }
}
