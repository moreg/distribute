package com.jdsw.distribute.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class Menu implements Serializable {
    // 菜单id
    private String id;
    // 菜单名称
    private String label;
    // 父菜单id
    private String parentId;

    Integer order;
    //父菜单名称
    private String parentName;

    // 子菜单
    private List<Menu> children;




}
