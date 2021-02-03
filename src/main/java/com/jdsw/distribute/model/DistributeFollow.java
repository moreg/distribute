package com.jdsw.distribute.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
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
     * 网络线索ID
     */
    private Integer networkId;
    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 录音
     */
    private String record;
    /**
     * 创建时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
    /**
     * 单号
     */
    private String trackId;
}
