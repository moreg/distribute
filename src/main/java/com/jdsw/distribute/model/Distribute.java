package com.jdsw.distribute.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
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
     * 客户来源
     */
    Integer source;
    /**
     * 客户状态
     */
    Integer state;
    /**
     * 最后跟进阶段
     */
    Integer lastFollowUp;
    /**
     * 最后个跟进内容
     */
    String lastFollowResult;
    /**
     * 最后跟进时间
     */
    Date lastFollowTime;
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
    String createTime;
    /**
     * 微信号
     */
    Integer weChat;
    /**
     * 生日
     */
    Date birthday;
    /**
     * 性别
     */
    Integer sex;
    /**
     * 行业
     */
    String industry;
    /**
     * 法人证件号
     */
    String corporateNumber;
    /**
     * 信用代码/识别代码
     */
    String creditNumber;
    /**
     * 注册地联系方式
     */
    String domicilePhone;
    /**
     * 注册地址
     */
    String domicile;
    /**
     * 经营规模
     */
    String scale;
    /**
     * 成立日期
     */
    String foundTime;
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
     * 经营状态
     */
    String operation;
    /**
     * 更多号码
     */
    String otherPhone;
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
}
