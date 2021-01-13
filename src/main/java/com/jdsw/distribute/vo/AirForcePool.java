package com.jdsw.distribute.vo;

import java.util.Date;

public class AirForcePool {

    private Integer id;
    private String trackId;
    private String name;
    private String corporateName;
    private String corporatePhone;
    private String registeredCapital;
    private String establishTime;
    private String operation;
    private Integer issue;
    private Integer state;
    private Date createTime;
    private String lastFollowName;
    private Integer source;
    private Date lastFollowTime;
    private String city;
    private String otherPhone;
    private String address;
    private Integer status;
    private String overdueTime;

    public String getOverdueTime() {
        return overdueTime;
    }

    public void setOverdueTime(String overdueTime) {
        this.overdueTime = overdueTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getCorporatePhone() {
        return corporatePhone;
    }

    public void setCorporatePhone(String corporatePhone) {
        this.corporatePhone = corporatePhone;
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getEstablishTime() {
        return establishTime;
    }

    public void setEstablishTime(String establishTime) {
        this.establishTime = establishTime;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastFollowName() {
        return lastFollowName;
    }

    public void setLastFollowName(String lastFollowName) {
        this.lastFollowName = lastFollowName;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Date getLastFollowTime() {
        return lastFollowTime;
    }

    public void setLastFollowTime(Date lastFollowTime) {
        this.lastFollowTime = lastFollowTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOtherPhone() {
        return otherPhone;
    }

    public void setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "AirForcePool{" +
                "id=" + id +
                ", trackId='" + trackId + '\'' +
                ", name='" + name + '\'' +
                ", corporateName='" + corporateName + '\'' +
                ", corporatePhone='" + corporatePhone + '\'' +
                ", registeredCapital='" + registeredCapital + '\'' +
                ", establishTime='" + establishTime + '\'' +
                ", operation='" + operation + '\'' +
                ", issue=" + issue +
                ", state=" + state +
                ", createTime=" + createTime +
                ", lastFollowName='" + lastFollowName + '\'' +
                ", source=" + source +
                ", lastFollowTime=" + lastFollowTime +
                ", city='" + city + '\'' +
                ", otherPhone='" + otherPhone + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", overdueTime='" + overdueTime + '\'' +
                '}';
    }
}
