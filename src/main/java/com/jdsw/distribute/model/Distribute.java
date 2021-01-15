package com.jdsw.distribute.model;

import java.math.BigDecimal;
import java.util.Date;

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
     * 联系人
     */
    String contacts;
    /**
     * 联系人手机号码
     */
    String contactsPhone;
    /**
     * 客户来源
     */
    Integer source;
    /**
     * 跟进次数
     */
    Integer followNumber;
    /**
     * 客户状态
     */
    Integer state;
    /**
     * 最后跟进阶段
     */
    Integer lastFollowUp;
    /**
     * 最后个金内容
     */
    String lastFollowContent;
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
     * 微信名称备注
     */
    String wechatName;
    /**
     * 微信号
     */
    Integer wechatNumber;
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
     * 是否指定订单
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
     * 过期时间
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

    public Integer getMsec() {
        return msec;
    }

    public void setMsec(Integer msec) {
        this.msec = msec;
    }

    public Long getSurplusTime() {
        return surplusTime;
    }

    public void setSurplusTime(Long surplusTime) {
        this.surplusTime = surplusTime;
    }

    public Integer getInvalid() {
        return invalid;
    }

    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Integer getLeaderSign() {
        return leaderSign;
    }

    public void setLeaderSign(Integer leaderSign) {
        this.leaderSign = leaderSign;
    }

    public String getOverdueTime() {
        return overdueTime;
    }

    public void setOverdueTime(String overdueTime) {
        this.overdueTime = overdueTime;
    }

    public Integer getActivation() {
        return activation;
    }

    public void setActivation(Integer activation) {
        this.activation = activation;
    }


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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getFollowNumber() {
        return followNumber;
    }

    public void setFollowNumber(Integer followNumber) {
        this.followNumber = followNumber;
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

    public String getLastFollowContent() {
        return lastFollowContent;
    }

    public void setLastFollowContent(String lastFollowContent) {
        this.lastFollowContent = lastFollowContent;
    }

    public Date getLastFollowTime() {
        return lastFollowTime;
    }

    public void setLastFollowTime(Date lastFollowTime) {
        this.lastFollowTime = lastFollowTime;
    }

    public String getLastFollowName() {
        return lastFollowName;
    }

    public void setLastFollowName(String lastFollowName) {
        this.lastFollowName = lastFollowName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName;
    }

    public Integer getWechatNumber() {
        return wechatNumber;
    }

    public void setWechatNumber(Integer wechatNumber) {
        this.wechatNumber = wechatNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCorporateNumber() {
        return corporateNumber;
    }

    public void setCorporateNumber(String corporateNumber) {
        this.corporateNumber = corporateNumber;
    }

    public String getCreditNumber() {
        return creditNumber;
    }

    public void setCreditNumber(String creditNumber) {
        this.creditNumber = creditNumber;
    }

    public String getDomicilePhone() {
        return domicilePhone;
    }

    public void setDomicilePhone(String domicilePhone) {
        this.domicilePhone = domicilePhone;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(String foundTime) {
        this.foundTime = foundTime;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getFirstFollowName() {
        return firstFollowName;
    }

    public void setFirstFollowName(String firstFollowName) {
        this.firstFollowName = firstFollowName;
    }

    public Integer getAppoint() {
        return appoint;
    }

    public void setAppoint(Integer appoint) {
        this.appoint = appoint;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getConduct() {
        return conduct;
    }

    public void setConduct(String conduct) {
        this.conduct = conduct;
    }

    public BigDecimal getPay() {
        return pay;
    }

    public void setPay(BigDecimal pay) {
        this.pay = pay;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getRecordingName() {
        return recordingName;
    }

    public void setRecordingName(String recordingName) {
        this.recordingName = recordingName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
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

    @Override
    public String toString() {
        return "Distribute{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", corporateName='" + corporateName + '\'' +
                ", corporatePhone='" + corporatePhone + '\'' +
                ", contacts='" + contacts + '\'' +
                ", contactsPhone='" + contactsPhone + '\'' +
                ", source=" + source +
                ", followNumber=" + followNumber +
                ", state=" + state +
                ", lastFollowUp=" + lastFollowUp +
                ", lastFollowContent='" + lastFollowContent + '\'' +
                ", lastFollowTime=" + lastFollowTime +
                ", lastFollowName='" + lastFollowName + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", wechatName='" + wechatName + '\'' +
                ", wechatNumber=" + wechatNumber +
                ", birthday=" + birthday +
                ", sex=" + sex +
                ", industry='" + industry + '\'' +
                ", corporateNumber='" + corporateNumber + '\'' +
                ", creditNumber='" + creditNumber + '\'' +
                ", domicilePhone='" + domicilePhone + '\'' +
                ", domicile='" + domicile + '\'' +
                ", scale='" + scale + '\'' +
                ", foundTime='" + foundTime + '\'' +
                ", trackId='" + trackId + '\'' +
                ", firstFollowName='" + firstFollowName + '\'' +
                ", appoint=" + appoint +
                ", leaderName='" + leaderName + '\'' +
                ", remarks='" + remarks + '\'' +
                ", city='" + city + '\'' +
                ", registeredCapital='" + registeredCapital + '\'' +
                ", issue=" + issue +
                ", department='" + department + '\'' +
                ", conduct='" + conduct + '\'' +
                ", pay=" + pay +
                ", cost=" + cost +
                ", dealTime='" + dealTime + '\'' +
                ", recordingName='" + recordingName + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", establishTime='" + establishTime + '\'' +
                ", operation='" + operation + '\'' +
                ", otherPhone='" + otherPhone + '\'' +
                ", address='" + address + '\'' +
                ", overdueTime='" + overdueTime + '\'' +
                ", activation=" + activation +
                ", leaderSign=" + leaderSign +
                ", branch='" + branch + '\'' +
                ", invalid=" + invalid +
                ", surplusTime=" + surplusTime +
                ", msec=" + msec +
                '}';
    }
}
