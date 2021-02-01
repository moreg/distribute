package com.jdsw.distribute.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class AirForcePool {

    private Integer id;
    private String trackId;
    private String corporateName;
    private String corporatePhone;
    private Integer issue;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;
    private String lastFollowName;
    private Integer source;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date lastFollowTime;
    private Integer status;
    private String overdueTime;
    private Integer leaderSign;
    private Integer activation;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    private  Date receivingTime;
    private String lastFollowResult;
    private Integer sign;
    private String proposer;
    private String activationTime;
    private BigDecimal total;
    private String grade;
}
