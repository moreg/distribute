package com.jdsw.distribute.vo;

import java.util.Date;

public class ComePool {
    private Integer id;
    private String contacts;
    private String contactsPhone;
    private String createTime;
    private String source;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "ComePool{" +
                "id=" + id +
                ", contacts='" + contacts + '\'' +
                ", contactsPhone='" + contactsPhone + '\'' +
                ", createTime='" + createTime + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
