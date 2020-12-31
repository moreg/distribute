package com.jdsw.distribute.vo;

import java.util.Date;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getLastFollowName() {
        return lastFollowName;
    }

    public void setLastFollowName(String lastFollowName) {
        this.lastFollowName = lastFollowName;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }
}
