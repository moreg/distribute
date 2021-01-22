package com.jdsw.distribute.enums;

public enum Department {
    CUSTOMER("线索管理员"),SALESMAN("业务员"),GENERAL("总经理"),DEPUTY("副总经理"),CHARGE("部门主管"),FINANCE("财务");
    public String value;
     Department(String value) {
        this.value = value;
    }

    public static void main(String[] args) {
        System.out.println( Department.CUSTOMER.value);

    }
}
