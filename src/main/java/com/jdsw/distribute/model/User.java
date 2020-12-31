package com.jdsw.distribute.model;

import java.io.Serializable;
import java.util.List;

/*@Data//生成get/set
@AllArgsConstructor//生成全参构造函数
@NoArgsConstructor//生成无参构造函数*/
public class User implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String name;

    private String remarks;

    private Integer state;


    private String addtime;

    private String salt;//加密密码的盐

    private List<Role> roles;
    private Integer currentPage;
    private Integer pageSize;
    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? null : addtime.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", remarks='" + remarks + '\'' +
                ", state=" + state +
                ", addtime='" + addtime + '\'' +
                ", salt='" + salt + '\'' +
                ", roles=" + roles +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", department='" + department + '\'' +
                '}';
    }
}