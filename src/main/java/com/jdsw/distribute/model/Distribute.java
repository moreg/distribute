package com.jdsw.distribute.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class Distribute {
    Integer id;
    /**
     * 客户名称
     */
    String name;
    /**
     * 法人名称
     */
    String corporateName;
    /**
     * 法人联系方式
     */
    String corporatePhone;
    /**
     * 法人联系方式
     */
    String corporatePhone2;
    /**
     * 法人联系方式
     */
    String corporatePhone3;
    /**
     * 客户来源
     */
    String source;
    /**
     * 客户状态
     */
    Integer state;
    /**
     * 最后跟进内容
     */
    String lastFollowResult;
    /**
     * 最后跟进时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    String lastFollowTime;
    /**
     * 最后跟进人
     */
    String lastFollowName;
    /**
     * 订单状态
     */
    Integer status;
    /**
     * 录入时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    Date createTime;
    /**
     * 微信号
     */
    String weChat;
    /**
     * 生日
     */
    Date birthday;

    /**
     * 行业
     */
    String industry;

    /**
     * 跟踪单编号
     */
    String trackId;
    /**
     * 第一接单人
     * @return
     */
    String firstFollowName;
    /**
     * 是否自建单
     */
    Integer appoint;
    /**
     *接单人上级主管
     */
    String leaderName;
    /**
     * 备注
     */
    String remarks;
    /**
     * 城市
     */
    String city;
    /**
     * 注册资本
     */
    String registeredCapital;
    /**
     * 是否分发
     */
    Integer issue;
    /**
     * 部门
     */
    String department;
    /**
     * 办理业务
     */
    String conduct;
    /**
     * 客户缴费
     */
    BigDecimal pay;
    /**
     * 业务成本
     */
    BigDecimal cost;
    /**
     * 成交日期
     */
    String dealTime;
    /**
     * 录单人
     */
    String recordingName;
    /**
     * 合同单号
     */
    String contractNo;
    /**
     * 成立日期
     */
    String establishTime;
    /**
     * 公示地址
     */
    String address;
    /**
     * 超时时间
     */
    String overdueTime;
    /**
     * 激活状态
     */
    Integer activation;
    /**
     * 主管分发标记
     */
    Integer leaderSign;
    /**
     * 分公司
     */
    String branch;
    /**
     * 无效线索标记
     */
    Integer invalid;
    /**
     * 剩余时间
     */
    Long surplusTime;
    /**
     * 超时时间（毫秒）
     */
    Integer msec;
    /**
     * 接单日期
     */
    String receivingTime;
    /**
     * 图片地址
     */
    String imgUrl;
    /**
     * 客服名称
     */
    String proposer;
    /**
     * 有效无效
     */
    Integer sign;
    /**
     * 消费总额
     */
    BigDecimal total;
    /**
     * 客户等级
     */
    String grade;
    /**
     * 激活人
     */
    String activationName;
    /*
    *
    *激活时间
     */
    String activationTime;
    /**
     * 积分
     */
    BigDecimal integral;
    /**
     * 下次跟进时间
     */
    String nextTime;
    /**
     * 是否超时退回
     */
    Integer overrun;
    /**
     * 录音地址
     */
    String record;
    /**
     * 业务编号
     */
    String businessNo;
    /**
     * 签订人
     */
    String signed;
    /**
     * 业务列表
     */
    List<Distribute> conducts;
    /**
     * 申诉时间
     */
    String applyTime;
    /**
     * 申请理由
     */
    String applyReason;
    /**
     * 申诉说明
     */
    String applyExplain;
    /**
     * 退回给谁
     */
    String returnName;
    /**
     * 退回时间
     */
    String returnTime;
    /**
     * 停止时间
     */
    String stopTime;
    /**
     * 停止说明
     */
    String stopExplain;
    /**
     * 分发时间
     */
    String outTime;
    /**
     * 分发人员
     */
    String outName;
    /**
     * 跟单池导航状态
     */
    Integer flag;
    /**
     * 客户号
     */
    String customerNo;
    /**
     * 停止理由
     */
    String stopReason;
    /**
     * 接收人
     */
    String receiveName;
    /**
     * 转交说明
     */
    String deliverExplain;
    /**
     * 操作
     */
    String operation;
}
