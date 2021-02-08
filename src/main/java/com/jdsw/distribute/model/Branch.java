package com.jdsw.distribute.model;

import lombok.Data;

@Data
public class Branch {
    private Integer id;
    private String name;
    private Integer parentId;
    private Integer order;


}
