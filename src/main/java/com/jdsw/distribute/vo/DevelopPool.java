package com.jdsw.distribute.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DevelopPool {

    private Integer id;
    private String trackId;
    private String corporateName;
    private String corporatePhone;
    private String lastFollowName;
    private Date lastFollowTime;
    private Integer activation;
    private String lastFollowResult;
    private String receivingTime;
    private BigDecimal total;
    private String grade;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;
    private String source;
}
