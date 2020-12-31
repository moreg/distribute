package com.jdsw.distribute.model;

import java.io.Serializable;
import java.util.List;

public class Permission implements Serializable {
    private Integer id;

    private String permissionname;

    private String remarks;

    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionname() {
        return permissionname;
    }

    public void setPermissionname(String permissionname) {
        this.permissionname = permissionname == null ? null : permissionname.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permissionname='" + permissionname + '\'' +
                ", remarks='" + remarks + '\'' +
                ", roles=" + roles +
                '}';
    }
}