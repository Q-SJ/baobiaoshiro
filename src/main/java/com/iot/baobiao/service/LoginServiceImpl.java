package com.iot.baobiao.service;

import com.iot.baobiao.dao.*;
import com.iot.baobiao.jooq.tables.pojos.User;
import com.iot.baobiao.jooq.tables.pojos.UserSms;
import com.iot.baobiao.shiro.MySQLRealm;
import com.iot.baobiao.util.DataReturnMap;
import com.iot.baobiao.util.ErrorReturnMap;
import com.iot.baobiao.util.OKReturnMap;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jia on 2016/11/3.
 */

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private Logger logger = Logger.getLogger(this.getClass());

    private String algorithmName = "md5";

    private final int hashIterations = 2;

    private static HttpClient httpClient = new HttpClient();

    private static Pattern pattern = Pattern.compile("returnstatus>Success");

    @Autowired
    RandomNumberGenerator rng;

    @Autowired
    private MySQLRealm mySQLRelam;

    @Autowired
    UserDaoInterface userDaoInterface;

    @Autowired
    UserSmsDaoInterface userSmsDaoInterface;

    @Autowired
    UserSiteDaoInterface userSiteDaoInterface;

    @Autowired
    Environment environment;

    @Autowired
    UserRolesDaoInterface userRolesDaoInterface;

    @Autowired
    PropertiesDao propertiesDao;

    private static String getSignCode() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append(String.valueOf((int) Math.floor(Math.random() * 9 + 1)));
        }
        return new String(builder);
    }

    @Override
    public Map<String, Object> sendMessage(String phonenum) {
        String code = getSignCode();
        PostMethod postMethod = new PostMethod(environment.getProperty("mes.url"));
        NameValuePair[] nameValuePairs = new NameValuePair[8];
        nameValuePairs[0] = new NameValuePair("action", environment.getProperty("mes.action"));
        nameValuePairs[1] = new NameValuePair("userid", environment.getProperty("mes.userid"));
        nameValuePairs[2] = new NameValuePair("mobile", phonenum);
        nameValuePairs[3] = new NameValuePair("content", environment.getProperty("mes.template").replace("@", code));
        nameValuePairs[4] = new NameValuePair("extno", environment.getProperty("mes.extno"));
        nameValuePairs[5] = new NameValuePair("sendTime", environment.getProperty("mes.sendTime"));
        nameValuePairs[6] = new NameValuePair("account", environment.getProperty("mes.account"));
        nameValuePairs[7] = new NameValuePair("password", environment.getProperty("mes.password"));
        postMethod.setRequestBody(nameValuePairs);
        String response;
        try {
            httpClient.executeMethod(postMethod);
            response = postMethod.getResponseBodyAsString();

            Matcher matcher = pattern.matcher(response);

            if (matcher.find()) {
                logger.info("向手机号" + phonenum + "发送验证码：" + code + "!");
                userSmsDaoInterface.insert(new UserSms(null, phonenum, code, 1, LocalDateTime.now()));
                return new OKReturnMap("发送成功！").getMap();
            }

        } catch (IOException e) {
            logger.error("向手机号" + phonenum + "信息发送失败！");
        }

        return new ErrorReturnMap("发送失败，请稍后再试！").getMap();
    }

    @Override
    public Map<String, Object> signup(String phonenum,
                                      String password,
                                      String code,
                                      String friend) {
        User user = userDaoInterface.fetchOneByPhonenum(phonenum);
        //如果用户数据库里面已经有这个手机号
        if (user != null) return new ErrorReturnMap("该手机号已经注册，请登录！").getMap();

        UserSms userSms = userSmsDaoInterface.fetchByPhonenum(phonenum);
        //state==0代表此验证码已经被用过
        if (!code.equals(userSms.getSmscode()) || userSms == null || userSms.getState() == 0) {
            return new ErrorReturnMap("验证码错误！").getMap();
        }
        int smsID = userSms.getId();
        userSmsDaoInterface.modifyStateByID(smsID);

        user = new User();
        user.setPhonenum(phonenum);

        if (friend != null) {
            user.setFriend(friend);
        }

        String salt = rng.nextBytes().toBase64();
        user.setSalt(salt);
        user.setPassword(new SimpleHash(algorithmName, password, salt, hashIterations).toBase64());
        user.setSignupTime(LocalDateTime.now());
        user.setLoginTime(LocalDateTime.now());
        user.setVip(1);

        user.setVipEndTime(LocalDateTime.now().plusDays(propertiesDao.getTrialDays()));
        int user_id = userDaoInterface.insertUserAndGetID(user);

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(phonenum, password);
        token.setRememberMe(true);
        currentUser.login(token);
        Session session = currentUser.getSession();
        session.setAttribute("user_id", user_id);
        session.setAttribute("phonenum", phonenum);
        return new OKReturnMap("您已注册成功并登录！").getMap();
    }

    @Override
    public Map<String,Object> login(String phonenum, String password, boolean askForSites) {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(phonenum, password);
        token.setRememberMe(true);
        currentUser.login(token);
        User user = userDaoInterface.returnUser(userDaoInterface.fetchOneByPhonenum(phonenum));
        int user_id = user.getId();
        userDaoInterface.modifyLoginTime(user_id);

        Session session = currentUser.getSession();
        session.setAttribute("user_id", user_id);
        session.setAttribute("phonenum", phonenum);
        DataReturnMap map = new DataReturnMap("user", user);
        if (askForSites) map.addData("sites", userSiteDaoInterface.fetchByUserId(user_id));
        return map.getMap();
    }

    @Override
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }

    @Override
    public Map<String, Object> changePassword(String phonenum, String oldPassword, String newPassword) {
        User user = userDaoInterface.fetchOneByPhonenum(phonenum);
        String salt = rng.nextBytes().toBase64();
        if (new SimpleHash(algorithmName, oldPassword, user.getSalt(), hashIterations).toBase64()
                .equals(user.getPassword())) {
            userDaoInterface.changePassword(phonenum, new SimpleHash(algorithmName, newPassword, user.getSalt(), hashIterations).toBase64(), salt);
            mySQLRelam.clearAuthenticationCache(phonenum);
            SecurityUtils.getSubject().logout();
        } else {
            return new ErrorReturnMap("提供的原有密码错误！").getMap();
        }
        return new OKReturnMap("密码已修改成功，请重新登录！").getMap();
    }

    @Override
    public Map<String, Object> forgetPassword(String phonenum, String password, String code) {
        User user = userDaoInterface.fetchOneByPhonenum(phonenum);
        //如果用户数据库里面没有这个手机号
        if (user == null) return new ErrorReturnMap("该手机号还未注册，请注册！").getMap();

        UserSms userSms = userSmsDaoInterface.fetchByPhonenum(phonenum);
        //state==0代表此验证码已经被用过
        if (!code.equals(userSms.getSmscode()) || userSms == null || userSms.getState() == 0) {
            return new ErrorReturnMap("验证码错误！").getMap();
        }
        int smsID = userSms.getId();
        userSmsDaoInterface.modifyStateByID(smsID);

        int user_id = user.getId();
        String salt = rng.nextBytes().toBase64();
        userDaoInterface.changePassword(phonenum, new SimpleHash(algorithmName, password, salt, hashIterations).toBase64(), salt);

        //先清除掉认证的缓存
        Subject currentUser = SecurityUtils.getSubject();
        mySQLRelam.clearAuthenticationCache(phonenum);
//        String realmName = currentUser.getPrincipals().getRealmNames().iterator().next();
//        //第一个参数为用户名,第二个参数为realmName
//        SimplePrincipalCollection principals = new SimplePrincipalCollection(phonenum,realmName);
//        currentUser.runAs(principals);
//        mySQLRelam.getAuthorizationCache().remove(currentUser.getPrincipals());
//        currentUser.releaseRunAs();

        UsernamePasswordToken token = new UsernamePasswordToken(phonenum, password);
        token.setRememberMe(true);
        currentUser.login(token);
        Session session = currentUser.getSession();
        session.setAttribute("user_id", user_id);
        session.setAttribute("phonenum", phonenum);
        return new OKReturnMap("您已成功设置密码并登录！").getMap();
    }

    @Override
    public void grant(int user_id, String role) {
        userRolesDaoInterface.insert(user_id, role);
        String phonenum = userDaoInterface.fetchPhonenumByID(user_id);
        mySQLRelam.clearAuthorizationCache(phonenum);
    }
}
