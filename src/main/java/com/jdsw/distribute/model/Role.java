package com.jdsw.distribute.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class Role implements Serializable {
    private Integer id;

    private String rolename;

    private String remarks;

    private List<Permission> permissions;

    private String department;

    private String group;

}