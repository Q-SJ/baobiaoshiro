/**
 * This class is generated by jOOQ
 */
package com.iot.baobiao.jooq.tables.records;


import com.iot.baobiao.jooq.tables.UserSms;

import java.time.LocalDateTime;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


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
public class UserSmsRecord extends UpdatableRecordImpl<UserSmsRecord> implements Record5<Integer, String, String, Integer, LocalDateTime> {

    private static final long serialVersionUID = 1227432486;

    /**
     * Setter for <code>nutch.user_sms.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>nutch.user_sms.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>nutch.user_sms.phonenum</code>.
     */
    public void setPhonenum(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>nutch.user_sms.phonenum</code>.
     */
    public String getPhonenum() {
        return (String) get(1);
    }

    /**
     * Setter for <code>nutch.user_sms.smscode</code>.
     */
    public void setSmscode(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>nutch.user_sms.smscode</code>.
     */
    public String getSmscode() {
        return (String) get(2);
    }

    /**
     * Setter for <code>nutch.user_sms.state</code>.
     */
    public void setState(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>nutch.user_sms.state</code>.
     */
    public Integer getState() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>nutch.user_sms.send_time</code>.
     */
    public void setSendTime(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>nutch.user_sms.send_time</code>.
     */
    public LocalDateTime getSendTime() {
        return (LocalDateTime) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, String, Integer, LocalDateTime> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, String, Integer, LocalDateTime> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return UserSms.USER_SMS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return UserSms.USER_SMS.PHONENUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return UserSms.USER_SMS.SMSCODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return UserSms.USER_SMS.STATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field5() {
        return UserSms.USER_SMS.SEND_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getPhonenum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getSmscode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value5() {
        return getSendTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSmsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSmsRecord value2(String value) {
        setPhonenum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSmsRecord value3(String value) {
        setSmscode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSmsRecord value4(Integer value) {
        setState(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSmsRecord value5(LocalDateTime value) {
        setSendTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSmsRecord values(Integer value1, String value2, String value3, Integer value4, LocalDateTime value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserSmsRecord
     */
    public UserSmsRecord() {
        super(UserSms.USER_SMS);
    }

    /**
     * Create a detached, initialised UserSmsRecord
     */
    public UserSmsRecord(Integer id, String phonenum, String smscode, Integer state, LocalDateTime sendTime) {
        super(UserSms.USER_SMS);

        set(0, id);
        set(1, phonenum);
        set(2, smscode);
        set(3, state);
        set(4, sendTime);
    }
}