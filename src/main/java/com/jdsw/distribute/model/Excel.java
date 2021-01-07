package com.jdsw.distribute.model;

import com.jdsw.distribute.util.excelRescoure;

public class Excel {
    @excelRescoure(value = "公司名称")
    private String name;
    @excelRescoure(value = "法定代表人")
    String corporateName;
    @excelRescoure(value = "企业公示的联系电话")
    String corporatePhone;
    @excelRescoure(value = "注册资本")
    String registeredCapital;
    @excelRescoure(value = "成立日期")
    String establishTime;
    @excelRescoure(value = "经营状态")
    String operation;
    @excelRescoure(value = "企业公示的联系电话（更多号码）")
    String otherPhone;
    @excelRescoure(value = "企业公示的地址")
    String address;

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
        return "Excel{" +
                "name='" + name + '\'' +
                ", corporateName='" + corporateName + '\'' +
                ", corporatePhone='" + corporatePhone + '\'' +
                ", registeredCapital='" + registeredCapital + '\'' +
                ", establishTime='" + establishTime + '\'' +
                ", operation='" + operation + '\'' +
                ", otherPhone='" + otherPhone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
