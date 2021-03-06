/**
 * This class is generated by jOOQ
 */
package com.iot.baobiao.jooq.tables.daos;


import com.iot.baobiao.jooq.tables.RolesPermissions;
import com.iot.baobiao.jooq.tables.records.RolesPermissionsRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


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
@Repository
public class RolesPermissionsDao extends DAOImpl<RolesPermissionsRecord, com.iot.baobiao.jooq.tables.pojos.RolesPermissions, Integer> {

    /**
     * Create a new RolesPermissionsDao without any configuration
     */
    public RolesPermissionsDao() {
        super(RolesPermissions.ROLES_PERMISSIONS, com.iot.baobiao.jooq.tables.pojos.RolesPermissions.class);
    }

    /**
     * Create a new RolesPermissionsDao with an attached configuration
     */
    @Autowired
    public RolesPermissionsDao(Configuration configuration) {
        super(RolesPermissions.ROLES_PERMISSIONS, com.iot.baobiao.jooq.tables.pojos.RolesPermissions.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.iot.baobiao.jooq.tables.pojos.RolesPermissions object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.iot.baobiao.jooq.tables.pojos.RolesPermissions> fetchById(Integer... values) {
        return fetch(RolesPermissions.ROLES_PERMISSIONS.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.iot.baobiao.jooq.tables.pojos.RolesPermissions fetchOneById(Integer value) {
        return fetchOne(RolesPermissions.ROLES_PERMISSIONS.ID, value);
    }

    /**
     * Fetch records that have <code>role IN (values)</code>
     */
    public List<com.iot.baobiao.jooq.tables.pojos.RolesPermissions> fetchByRole(String... values) {
        return fetch(RolesPermissions.ROLES_PERMISSIONS.ROLE, values);
    }

    /**
     * Fetch records that have <code>permission IN (values)</code>
     */
    public List<com.iot.baobiao.jooq.tables.pojos.RolesPermissions> fetchByPermission(String... values) {
        return fetch(RolesPermissions.ROLES_PERMISSIONS.PERMISSION, values);
    }
}
