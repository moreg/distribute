package com.jdsw.distribute.vo;

import lombok.Data;

import java.util.Date;
@Data
public class AirForcePool {

    private Integer id;
    private String trackId;
    private String corporateName;
    private String corporatePhone;
    private Integer issue;
    private Date createTime;
    private String lastFollowName;
    private Integer source;
    private Date lastFollowTime;
    private Integer status;
    private String overdueTime;
    private Integer leaderSign;
    private Integer activation;
    private  Date receivingTime;
    private String lastFollowResult;
}
