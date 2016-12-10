/**
 * This class is generated by jOOQ
 */
package com.iot.baobiao.jooq.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserSms implements Serializable {

    private static final long serialVersionUID = -1705931809;

    private Integer       id;
    private String        phonenum;
    private String        smscode;
    private Integer       state;
    private LocalDateTime sendTime;

    public UserSms() {}

    public UserSms(UserSms value) {
        this.id = value.id;
        this.phonenum = value.phonenum;
        this.smscode = value.smscode;
        this.state = value.state;
        this.sendTime = value.sendTime;
    }

    public UserSms(
        Integer       id,
        String        phonenum,
        String        smscode,
        Integer       state,
        LocalDateTime sendTime
    ) {
        this.id = id;
        this.phonenum = phonenum;
        this.smscode = smscode;
        this.state = state;
        this.sendTime = sendTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhonenum() {
        return this.phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getSmscode() {
        return this.smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public LocalDateTime getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserSms (");

        sb.append(id);
        sb.append(", ").append(phonenum);
        sb.append(", ").append(smscode);
        sb.append(", ").append(state);
        sb.append(", ").append(sendTime);

        sb.append(")");
        return sb.toString();
    }
}