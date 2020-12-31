package com.jdsw.distribute.model;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {
    // 菜单id
    private String id;
    // 菜单名称
    private String label;
    // 父菜单id
    private String parentId;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    Integer order;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    // 子菜单
    private List<Menu> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", parentId='" + parentId + '\'' +
                ", order=" + order +
                ", children=" + children +
                '}';
    }
}
