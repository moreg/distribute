package com.jdsw.distribute.model;

import java.util.Date;

public class DistributeFollow {
    private Integer id;
    /**
     * 跟进人名字
     */
    private String followName;
    /**
     * 跟进时间
     */
    private String followTime;
    /**
     * 跟进结果
     */
    private String followResult;
    /**
     * 联系方式
     */
    private Integer contactType;
    /**
     * 网络线索ID
     */
    private Integer networkId;
    /**
     * 图片地址
     */
    private String imgUrl;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 录音
     */
    private String record;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 客户状态
     */
    private Integer state;
    /**
     * 跟进阶段
     */
    private Integer lastFollowUp;
    /**
     * 退回客户池标识
     */
    private Integer returnPool;
    /*
     *退回主管标识
     */
    private Integer returnLeader;

    public Integer getReturnPool() {
        return returnPool;
    }

    public void setReturnPool(Integer returnPool) {
        this.returnPool = returnPool;
    }

    public Integer getReturnLeader() {
        return returnLeader;
    }

    public void setReturnLeader(Integer returnLeader) {
        this.returnLeader = returnLeader;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getLastFollowUp() {
        return lastFollowUp;
    }

    public void setLastFollowUp(Integer lastFollowUp) {
        this.lastFollowUp = lastFollowUp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFollowName() {
        return followName;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public String getFollowTime() {
        return followTime;
    }

    public void setFollowTime(String followTime) {
        this.followTime = followTime;
    }

    public String getFollowResult() {
        return followResult;
    }

    public void setFollowResult(String followResult) {
        this.followResult = followResult;
    }

    public Integer getContactType() {
        return contactType;
    }

    public void setContactType(Integer contactType) {
        this.contactType = contactType;
    }

    public Integer getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Integer networkId) {
        this.networkId = networkId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DistributeFollow{" +
                "id=" + id +
                ", followName='" + followName + '\'' +
                ", followTime='" + followTime + '\'' +
                ", followResult='" + followResult + '\'' +
                ", contactType=" + contactType +
                ", networkId=" + networkId +
                ", imgUrl='" + imgUrl + '\'' +
                ", remarks='" + remarks + '\'' +
                ", record='" + record + '\'' +
                ", createTime=" + createTime +
                ", state=" + state +
                ", lastFollowUp=" + lastFollowUp +
                ", returnPool=" + returnPool +
                ", returnLeader=" + returnLeader +
                '}';
    }
}
