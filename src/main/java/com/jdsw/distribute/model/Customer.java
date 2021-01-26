package com.jdsw.distribute.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class Customer {
    private Integer id;
    private String corporateName;
    private String corporatePhone;
    private String corporatePhone2;
    private String corporatePhone3;
    private String trackId;
    private String grade;
    private BigDecimal integral;
    private String activationName;
    private String activationTime;
    private String weChat;

}
