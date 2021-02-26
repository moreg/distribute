package com.jdsw.distribute.service.impl;

import com.alibaba.fastjson.JSON;
import com.jdsw.distribute.dao.MenuDao;
import com.jdsw.distribute.dao.UserDao;
import com.jdsw.distribute.enums.Department;
import com.jdsw.distribute.model.Branch;
import com.jdsw.distribute.model.Menu;
import com.jdsw.distribute.model.SysMune;
import com.jdsw.distribute.service.MenuService;
import com.jdsw.distribute.util.Rand;
import com.jdsw.distribute.vo.MenuVo;
import com.jdsw.distribute.vo.UsersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "MenuService")
public class MenuServiceImpl  implements MenuService {

    @Autowired
    private MenuDao menuMapper;
    @Autowired
    private UserDao userDao;
    @Override
    public Comparator<Menu> order() {
        Comparator<Menu> comparator = new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                if(o1.getOrder() != o2.getOrder()){
                    return o1.getOrder() - o2.getOrder();
                }
                return 0;
            }
        };
        return comparator;
    }

    @Override
    public List<SysMune> findTree(String username) {
        Map<String,Object> data = new HashMap<String,Object>();
        try {//查询所有菜单
            List<SysMune> allMenu = menuMapper.findTree(username);
            //根节点
           /*List<Menu> rootMenu = new ArrayList<Menu>();
            for (Menu nav : allMenu) {
                if(nav.getParentId().equals("-1")){//父节点是0的，为根节点。
                    rootMenu.add(nav);
                }
            }
            //根据Menu类的order排序
            Collections.sort(rootMenu, order());
            //为根菜单设置子菜单，getClild是递归调用的
            for (Menu nav : rootMenu) {
                // 获取根节点下的所有子节点 使用getChild方法
                List<Menu> childList = getChild(nav.getId(), allMenu);
                nav.setChildren(childList);//给根节点设置子节点
            }*/
            /**
             * 输出构建好的菜单数据。
             *
             */
            data.put("list", allMenu);
            return allMenu;
        } catch (Exception e) {
            data.put("success", "false");
            data.put("list", new ArrayList());
            //return rootMenu;
        }
        return null;
    }

    @Override
    public List<Menu> findBranch() {
        Map<String,Object> data = new HashMap<String,Object>();
        try {//查询所有菜单
            List<Menu> allMenu = menuMapper.findBranch();
            //根节点
            List<Menu> rootMenu = new ArrayList<Menu>();
            for (Menu nav : allMenu) {
                if(nav.getParentId().equals("0")){//父节点是0的，为根节点。
                    rootMenu.add(nav);
                }
            }
            /* 根据Menu类的order排序 */
            Collections.sort(rootMenu, order());
            //为根菜单设置子菜单，getClild是递归调用的
            for (Menu nav : rootMenu) {
                /* 获取根节点下的所有子节点 使用getChild方法*/
                List<Menu> childList = getChild(nav.getId(), allMenu);
                nav.setChildren(childList);//给根节点设置子节点
            }
            /**
             * 输出构建好的菜单数据。
             *
             */
            data.put("list", rootMenu);
            return rootMenu;
        } catch (Exception e) {
            data.put("success", "false");
            data.put("list", new ArrayList());
            //return rootMenu;
        }
        return null;
    }

    @Override
    public List<Menu> getSection(String id) {
        List<Menu> allMenu = menuMapper.findBranch();
        List<Menu> childList = getChild2(id,allMenu);
        return childList;
    }

    /**
     * 下属客户
     * @param id
     * @param allMenu
     * @return
     */
    public List<Menu> getChild3(String id, List<Menu> allMenu) {
        //子菜单
        List<Menu> childList = new ArrayList<Menu>();

        for (Menu nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if(nav.getParentId().equals(id)){
                childList.add(nav);
            }
        }
        //递归
        for (Menu nav : childList) {
            nav.setChildren(getChild3(nav.getId(), allMenu));
        }
        Collections.sort(childList,order());//排序
        //如果节点下没有子节点，返回一个空List（递归退出）
        if(childList.size() == 0){
            return new ArrayList<Menu>();
        }
        return childList;
    }

    public List<Menu> getChild2(String id, List<Menu> allMenu) {
        //子菜单
        List<Menu> childList = new ArrayList<Menu>();

        for (Menu nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if(nav.getParentId().equals(id)){
                childList.add(nav);
            }
        }
        //递归
        for (Menu nav : childList) {
            nav.setChildren(getChild2(nav.getId(), allMenu));
        }
        Collections.sort(childList,order());//排序
        //如果节点下没有子节点，返回一个空List（递归退出）
        if(childList.size() == 0){
            return new ArrayList<Menu>();
        }
        return childList;
    }

    /**
     * 获取子节点
     * @param id 父节点id
     * @param allMenu 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    @Override
    public List<Menu> getChild(String id, List<Menu> allMenu) {
        //子菜单
        List<Menu> childList = new ArrayList<Menu>();

        for (Menu nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if(nav.getParentId().equals(id)){
                childList.add(nav);
            }
        }
        //递归
        for (Menu nav : childList) {
            if (nav.getLabel().equals("全部")){
                return new ArrayList<Menu>();
            }
            nav.setChildren(getChild(nav.getId(), allMenu));
        }
        Collections.sort(childList,order());//排序
        //如果节点下没有子节点，返回一个空List（递归退出）
        if(childList.size() == 0){
            return new ArrayList<Menu>();
        }
        return childList;
    }

    @Override
    public List<SysMune> getMenuLsit(String username) {
        List<SysMune> list = menuMapper.getMenuList(username);
        return list;
    }

    @Override
    public List<String> getSubordinate(Map map) {
      /*  UsersVo user = userDao.findRoleByUserName3((String) map.get("username"));
        System.out.println(user);
        if (user.getRolename().equals(Department.CHARGE.value)){
            return userDao.qureyGroup(user.getId());
        }else if (user.getRolename().equals(Department.GENERAL.value)){

        }*/
        return null;
    }

    @Override
    public List getsubordinateMenuList(Map map) {
        String username = (String) map.get("username");
        String branch = (String) map.get("branch");
        UsersVo usersVo = userDao.queryBranch(username);
        Set set = userDao.findRoleByUserName2(username);
        List list = new ArrayList();
        for (Object str : set) {
            if (str.equals(Department.CHARGE.value)) {//主管
                if (branch.equals(usersVo.getBranch())){
                    list.add(usersVo.getGroup());
                    return list;
                }
            }
            if (str.equals(Department.GENERAL.value) || str.equals(Department.DEPUTY.value)){
                List<Menu> allMenu = menuMapper.findBranch();
                if (branch.equals("南宁分公司")){
                    List<Menu> childList = getChild3("14",allMenu);
                    return childList;
                }else if (branch.equals("梧州分公司")){
                    List<Menu> childList = getChild3("15",allMenu);
                    return childList;
                }
            }
        }
        return null;
    }

    @Override
    public List<Menu> businessMenu() {
        Map<String,Object> data = new HashMap<String,Object>();
        try {//查询所有菜单
            List<Menu> allMenu = menuMapper.businessMenu();
            //根节点
            List<Menu> rootMenu = new ArrayList<Menu>();
            for (Menu nav : allMenu) {
                if(nav.getParentId().equals("0")){//父节点是0的，为根节点。
                    rootMenu.add(nav);
                }
            }
            /* 根据Menu类的order排序 */
            Collections.sort(rootMenu, order());
            //为根菜单设置子菜单，getClild是递归调用的
            for (Menu nav : rootMenu) {
                /* 获取根节点下的所有子节点 使用getChild方法*/
                List<Menu> childList = getChild3(nav.getId(), allMenu);
                nav.setChildren(childList);//给根节点设置子节点
            }
            data.put("list", rootMenu);
            return rootMenu;
        } catch (Exception e) {
            data.put("success", "false");
            data.put("list", new ArrayList());
            //return rootMenu;
        }
        return null;
    }

    @Override
    public List<MenuVo> kSourceMenu(String type) {
        List<MenuVo> list = null;
        if ("K".equals(type)) {
            list = menuMapper.kSourceMenu();
        }else if ("L".equals(type)){
            list = menuMapper.lSourceMenu();
        }

        return findMenuvo(list);
    }

    /**
     * 递归遍历菜单
     * @param allMenu 所有菜单list
     * @return
     */
    public List<MenuVo> findMenuvo(List<MenuVo> allMenu) {
        Map<String,Object> data = new HashMap<String,Object>();
        try {
            //根节点
            List<MenuVo> rootMenu = new ArrayList<MenuVo>();
            for (MenuVo nav : allMenu) {
                if(nav.getParentId().equals("0")){//父节点是0的，为根节点。
                    rootMenu.add(nav);
                }
            }
            /* 根据Menu类的order排序 */
            //Collections.sort(rootMenu, order());
            //为根菜单设置子菜单，getClild是递归调用的
            for (MenuVo nav : rootMenu) {
                /* 获取根节点下的所有子节点 使用getChild方法*/
                List<MenuVo> childList = getChildVo(nav.getId(), allMenu);
                nav.setChildren(childList);//给根节点设置子节点
            }
            /**
             * 输出构建好的菜单数据。
             *
             */
            data.put("list", rootMenu);
            return rootMenu;
        } catch (Exception e) {
            data.put("success", "false");
            data.put("list", new ArrayList());
            //return rootMenu;
        }
        return null;
    }
    public List<MenuVo> getChildVo(String id, List<MenuVo> allMenu) {
        //子菜单
        List<MenuVo> childList = new ArrayList<MenuVo>();

        for (MenuVo nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if(nav.getParentId().equals(id)){
                childList.add(nav);
            }
        }
        //递归
        for (MenuVo nav : childList) {
            nav.setChildren(getChildVo(nav.getId(), allMenu));
        }
        //Collections.sort(childList,order());//排序
        //如果节点下没有子节点，返回一个空List（递归退出）
        if(childList.size() == 0){
            return new ArrayList<MenuVo>();
        }
        return childList;
    }
}
