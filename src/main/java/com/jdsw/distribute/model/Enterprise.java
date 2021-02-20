package com.jdsw.distribute.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Enterprise {
    private Integer id;
    private String name;
    private String source;
    private String corporateName;
    private String corporatePhone;
    private String relation;
    private String addName;
    private String addTime;
    private String registeredCapital;
    private String establishTime;
    private String address;
    private String corporatePhone2;
    private String corporatePhone3;
    private String trackId;
    private String lastFollowName;
}
