package com.jdsw.distribute.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class SysLog implements Serializable {
     private String optId;
     private String loginId;
     private String loginName;
     private String ipAddress;
     private String methodName;
     private String methodRemark;
     private String operatingcontent;
     private String optDate;
    //模糊条件

    private String serchCondition;


}
