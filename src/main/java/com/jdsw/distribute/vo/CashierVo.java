package com.jdsw.distribute.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;
    private String source;
    private String lastFollowName;
    private String trackId;
    private Integer orderState;
    private String contractNo;
    private String dealTime;
    private String conduct;
    private BigDecimal pay;
    /**
     * 业务编号
     */
    private String businessNo;
    /**
     * 签订人
     */
    private String signed;
}
