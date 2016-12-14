package com.iot.baobiao.config;

import com.iot.baobiao.shiro.AddPrincipalToSessionFilter;
import com.iot.baobiao.shiro.MySQLRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jia on 2016/11/2.
 */
@EnableCaching
@Configuration
public class ShiroConfig {

    private final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Bean
    public SessionDAO sessionDAO() {
        return new EnterpriseCacheSessionDAO();
    }

    //使用Shiro自带的Session管理器
    @Bean
    public WebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO());
//        sessionManager.setGlobalSessionTimeout(300000);
        //设置Cookie中返回的SessionID的名字，默认是JSESSIONID
        sessionManager.getSessionIdCookie().setName("BAOBIAOSESSIONID");
        return sessionManager;
    }

    @Bean
    public CacheManager shiroCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

    @Bean
    public MySQLRealm realm() {
        MySQLRealm realm = new MySQLRealm();
        realm.setCredentialsMatcher(credentialsMatcher());
        realm.setCachingEnabled(true);
        realm.setAuthenticationCacheName("authenticationCache");
        realm.setAuthenticationCachingEnabled(true);
        realm.setAuthorizationCachingEnabled(true);
//        realm.setCacheManager(cacheManager());
        realm.init();
        return realm;
    }

    @Bean
    public WebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(shiroCacheManager());
        securityManager.setRealm(realm());
        return securityManager;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    //以下两个Bean是为了使用Shiro的注解
    @Bean
    @DependsOn(value="lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(){
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(new Object[]{securityManager()});

        return methodInvokingFactoryBean;
    }

    @Bean
    public RandomNumberGenerator randomNumberGenerator() {
        return new SecureRandomNumberGenerator();
    }

    @Bean
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setStoredCredentialsHexEncoded(false);
        return credentialsMatcher;
    }

    //Shiro自定义过滤器
    @Bean
    public AddPrincipalToSessionFilter addPrincipalToSessionFilter() {
        return new AddPrincipalToSessionFilter();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

        Map<String, Filter> map = new HashMap<>();
        map.put("addPrincipal", addPrincipalToSessionFilter());
        shiroFilter.setFilters(map);
//        shiroFilter.

        Map<String, String> definitionsMap = new HashMap<String, String>();
        definitionsMap.put("/pay/notify", "anon");
        definitionsMap.put("/pay/testRabbit", "anon");
        definitionsMap.put("/unauthenticated", "anon");
        definitionsMap.put("/login", "anon");
        definitionsMap.put("/verification", "anon");
        definitionsMap.put("/forgetPassword", "anon");
        definitionsMap.put("/signup", "anon");
        definitionsMap.put("/index.jsp", "anon");
        definitionsMap.put("/", "anon");
        definitionsMap.put("/pay/alipay", "authc");
        definitionsMap.put("/admin/**", "authc, roles[admin]");
        definitionsMap.put("/**", "addPrincipal, user");
        shiroFilter.setFilterChainDefinitionMap(definitionsMap);

        shiroFilter.setLoginUrl("/unauthenticated");
        shiroFilter.setUnauthorizedUrl("/unauthorized");
        shiroFilter.setSecurityManager(securityManager());

        logger.info("Shiro Filters: " + shiroFilter.getFilters());
        return shiroFilter;
    }
}
