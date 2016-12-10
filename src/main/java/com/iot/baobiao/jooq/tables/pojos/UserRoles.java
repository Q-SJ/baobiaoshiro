/**
 * This class is generated by jOOQ
 */
package com.iot.baobiao.jooq.tables.pojos;


import java.io.Serializable;

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
public class UserRoles implements Serializable {

    private static final long serialVersionUID = 1331374662;

    private Integer id;
    private Integer userId;
    private String  role;

    public UserRoles() {}

    public UserRoles(UserRoles value) {
        this.id = value.id;
        this.userId = value.userId;
        this.role = value.role;
    }

    public UserRoles(
        Integer id,
        Integer userId,
        String  role
    ) {
        this.id = id;
        this.userId = userId;
        this.role = role;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserRoles (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(role);

        sb.append(")");
        return sb.toString();
    }
}