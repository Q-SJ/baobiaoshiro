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
public class RolesPermissions implements Serializable {

    private static final long serialVersionUID = -2039489455;

    private Integer id;
    private String  role;
    private String  permission;

    public RolesPermissions() {}

    public RolesPermissions(RolesPermissions value) {
        this.id = value.id;
        this.role = value.role;
        this.permission = value.permission;
    }

    public RolesPermissions(
        Integer id,
        String  role,
        String  permission
    ) {
        this.id = id;
        this.role = role;
        this.permission = permission;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPermission() {
        return this.permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RolesPermissions (");

        sb.append(id);
        sb.append(", ").append(role);
        sb.append(", ").append(permission);

        sb.append(")");
        return sb.toString();
    }
}
