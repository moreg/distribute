package com.jdsw.distribute.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data//生成get/set

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
    private String group;



}