package com.jdsw.distribute.model;

import com.jdsw.distribute.util.excelRescoure;
import lombok.Data;

@Data
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


}
