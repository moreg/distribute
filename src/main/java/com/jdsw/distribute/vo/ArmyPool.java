package com.jdsw.distribute.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ArmyPool {
    private Integer id;
    private String trackId;
    private String name;
    private String corporateName;
    private String corporatePhone;
    private String registeredCapital;
    private String establishTime;
    private Integer issue;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;
    private Integer source;
    private String address;
    private String proposer;
    private Integer status;
    private Integer activation;
    private Integer sign;
}
