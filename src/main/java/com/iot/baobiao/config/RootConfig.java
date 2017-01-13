package com.iot.baobiao.config;

import com.iot.baobiao.exception.JOOQToSpringExceptionTranslator;
import com.iot.baobiao.executelistener.LoggingExecuteListener;
import com.iot.baobiao.pojo.Discount;
import com.iot.baobiao.pojo.VipInfo;
import org.apache.commons.httpclient.HttpClient;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * Created by ja on 2016/6/22.
 */

@Configuration
@Import({RabbitMQConfig.class, WebSocketConfig.class, ShiroConfig.class})
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages = {"com.iot.baobiao.dao", "com.iot.baobiao.service"})
public class RootConfig {

    @Autowired
    private Environment environment;

//    @Bean   //配置此Bean是为了使用属性占位符来注入值
//    public PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }

    //配置MySQL数据源，此为DBCP数据源连接池
    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("jdbc.driver"));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        dataSource.setMaxActive(30);
        dataSource.setMaxIdle(5);
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("select 1 from dual");
        return dataSource;
    }


    //声明缓存管理器，在ShiroConfig里面配置
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }


    //以下7个Bean用于配置JOOQ
    @Bean
    public TransactionAwareDataSourceProxy transactionAwareDataSource() {
        return new TransactionAwareDataSourceProxy(dataSource());
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(transactionAwareDataSource());
    }

    @Bean
    public JOOQToSpringExceptionTranslator exceptionTransformer() {
        return new JOOQToSpringExceptionTranslator();
    }

    @Bean
    public DefaultDSLContext dsl() {
        return new DefaultDSLContext(configuration());
    }

    @Bean
    public DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(connectionProvider());
        jooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTransformer()));

        SQLDialect dialect = SQLDialect.valueOf(environment.getProperty("jooq.sql.dialect"));
        jooqConfiguration.set(dialect);
//        jooqConfiguration.set(new DefaultExecuteListenerProvider(loggingExecuteListener(dialect)));

        return jooqConfiguration;
    }

    //自定义JOOQ的ExecuteListener类，用于记录执行的SQL语句
//    @Bean
//    public LoggingExecuteListener loggingExecuteListener(SQLDialect dialect) {
//        return new LoggingExecuteListener(dialect);
//    }

    //配置活动的Bean
    @Bean
    public Discount discount() {
        Discount discount = new Discount();
        discount.setName("无活动");
        discount.setDescription("");
        discount.setPrice(0);
        discount.setNum(0);
        discount.setBeginTime(null);
        discount.setEndTime(null);
        return discount;
    }

    @Bean
    public VipInfo vipInfo() {
        VipInfo info = new VipInfo();
        info.setDays(30);
        return info;
    }
}
