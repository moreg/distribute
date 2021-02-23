package com.jdsw.distribute.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 业务表
 */
@Data
public class Business {
    private Integer id;
    private String corporatePhone;
    private String contractNo;
    private String conduct;
    private BigDecimal pay;
    private String businessNo;
    private String dealTime;
}
