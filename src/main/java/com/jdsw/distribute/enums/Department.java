package com.jdsw.distribute.enums;

public enum Department {
    AirCUSTOMER("空军线索管理员"),ARMCUSTOMER("陆军线索管理员"),SALESMAN("普通员工"),
    GENERAL("总经理"),DEPUTY("副总经理"),CHARGE("主管"),FINANCE("财务"),ADMIN("超级管理员"),
    AIRCHARGE("空军线索主管"),CLUECHARGE("线索主管")
    ;
    public String value;
     Department(String value) {
        this.value = value;
    }

}
