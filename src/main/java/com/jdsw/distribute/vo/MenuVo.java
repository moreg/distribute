package com.jdsw.distribute.vo;

import com.jdsw.distribute.model.Menu;
import lombok.Data;

import java.util.List;
@Data
public class MenuVo {
    // 菜单id
    private String id;
    // 菜单名称
    private String label;
    // 父菜单id
    private String parentId;

    Integer order;
    //父菜单名称
    private String parentName;

    private String value;
    // 子菜单
    private List<MenuVo> children;

}
