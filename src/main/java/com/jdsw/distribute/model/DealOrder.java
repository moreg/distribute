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
    private String recordingName;
    private String dealTime;
    private String contractNo;
    private String conduct;
    private BigDecimal pay;
    private BigDecimal cost;
}
