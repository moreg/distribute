package com.jdsw.distribute.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
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
    private String source;
    private String address;
    private String proposer;
    private Integer status;
    private Integer activation;
    private Integer sign;
    private String lastFollowName;
    private BigDecimal total;
    private String grade;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String receivingTime;
    private String lastFollowResult;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date activationTime;
    private String activationName;
    private  Integer overrun;
    private String overdueTime;
    private Integer leaderSign;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastFollowTime;
    private String weChat;


}
