package com.iot.baobiao.shiro;

import com.iot.baobiao.dao.UserDaoInterface;
import com.iot.baobiao.jooq.tables.pojos.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jia on 2016/11/2.
 */
public class MySQLRealm extends AuthorizingRealm {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserDaoInterface userDaoInterface;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String phonenum = (String) principalCollection.getPrimaryPrincipal();
        logger.info("正在验证用户" + phonenum + "的权限......");
        info.setRoles(userDaoInterface.fetchRoleByPhonenum(phonenum));
        info.setStringPermissions(userDaoInterface.fetchPermissionByPhonenum(phonenum));
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        String phonenum = (String) authenticationToken.getPrincipal();
        logger.info("正在验证用户" + phonenum + "的身份......");
        User user = userDaoInterface.fetchOneByPhonenum(phonenum);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                user.getPhonenum(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName());
        return info;
    }

    public void clearAuthenticationCache(String phonenum) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(phonenum, getName());
        clearCachedAuthenticationInfo(principals);
    }

    public void clearAuthorizationCache(String phonenum) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(phonenum, getName());
        clearAuthorizationCache(phonenum);
    }
}
