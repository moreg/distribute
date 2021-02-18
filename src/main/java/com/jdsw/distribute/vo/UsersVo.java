package com.jdsw.distribute.vo;

import lombok.Data;

@Data
public class UsersVo {
    private Integer id;
    private String username;
    private String addTime;
    private String name;
    private String remarks;
    private String rolename;
    private Integer leader;
    private String department;
    private String branch;
    private String group;
    private String oldPassword;
    private String password1;
    private String password2;

}
