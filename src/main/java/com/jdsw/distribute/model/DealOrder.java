package com.jdsw.distribute.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class DealOrder {
    private Integer id;
    private  String name;
    private String corporateName;
    private String corporatePhone;
    private String createTime;
    private String source;
    private String lastFollowName;
    private String trackId;
    private Integer orderState;
    /**
     * 录单人
     */
    private String recordingName;
    /**
     * 录入时间
     */
    private String dealTime;
    private String contractNo;
    /**
     * 业务
     */
    private String conduct;
    /**
     * 金额
     */
    private BigDecimal pay;
    private BigDecimal cost;
    /**
     * 业务编号
     */
    private String businessNo;
    /**
     * 签订人
     */
    private String signed;
}
