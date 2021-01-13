package com.jdsw.distribute.vo;

public class UsersVo {
    private Integer id;
    private String username;
    private String addTime;
    private String name;
    private String remarks;
    private String rolename;
    private String leader;
    private String department;
    private String branch;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
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
        this.username = username;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "UsersVo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", addTime='" + addTime + '\'' +
                ", name='" + name + '\'' +
                ", remarks='" + remarks + '\'' +
                ", rolename='" + rolename + '\'' +
                ", leader='" + leader + '\'' +
                ", department='" + department + '\'' +
                ", branch='" + branch + '\'' +
                '}';
    }
}
