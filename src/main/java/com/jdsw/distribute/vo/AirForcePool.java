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
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    private String lastFollowName;
    private String source;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastFollowTime;
    private Integer status;
    private String overdueTime;
    private Integer leaderSign;
    private Integer activation;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private  Date receivingTime;
    private String lastFollowResult;
    private Integer sign;
    private String proposer;
    private String activationTime;
    private BigDecimal total;
    private String grade;
    private Integer overrun;
    private String weChat;
    /**
     * 申请时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date applyTime;
    /**
     * 申请理由
     */
    private String applyReason;
    /**
     * 申请说明
     */
    private String applyExplain;
    /**
     * 退回给谁
     */
    private String returnName;
    /**
     * 退回时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date returnTime;
    /**
     * 停止时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date stopTime;
    /**
     * 停止说明
     */
    private String stopExplain;
    /**
     * 分发时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date outTime;
    /**
     * 分发人员
     */
    private String outName;
    /**
     * 签订人
     */
    private String signed;
}
