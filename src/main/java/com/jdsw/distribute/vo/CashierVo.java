package com.jdsw.distribute.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 成交订单
 */
@Data
public class CashierVo {

    private Integer id;
    private String name;
    private String corporateName;
    private String corporatePhone;
    private Date createTime;
    private Integer source;
    private String lastFollowName;
    private String trackId;
    private Integer orderState;
    private String contractNo;
    private String dealTime;
    private String conduct;
    private BigDecimal pay;
}
