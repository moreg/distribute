package com.jdsw.distribute.vo;

public class RecordingVo {
    private String name;
    private String corporateName;
    private Integer source;
    private String trackId;
    private String createTime;
    private String lastFollowName;


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

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastFollowName() {
        return lastFollowName;
    }

    public void setLastFollowName(String lastFollowName) {
        this.lastFollowName = lastFollowName;
    }
}
