package com.jdsw.distribute.model;

import java.io.Serializable;

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

    public String getOptId() {
        return optId;
    }

    public void setOptId(String optId) {
        this.optId = optId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodRemark() {
        return methodRemark;
    }

    public void setMethodRemark(String methodRemark) {
        this.methodRemark = methodRemark;
    }

    public String getOperatingcontent() {
        return operatingcontent;
    }

    public void setOperatingcontent(String operatingcontent) {
        this.operatingcontent = operatingcontent;
    }

    public String getOptDate() {
        return optDate;
    }

    public void setOptDate(String optDate) {
        this.optDate = optDate;
    }

    public String getSerchCondition() {
        return serchCondition;
    }

    public void setSerchCondition(String serchCondition) {
        this.serchCondition = serchCondition;
    }
}
