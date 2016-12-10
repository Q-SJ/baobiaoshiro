package com.iot.baobiao.exception;

import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultExecuteListener;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

/**
 * Created by jia on 2016/10/26.
 *
 *
 * In order to make exceptions thrown from jOOQ execution consistent with Spring support for database access,
 * we need to translate them into subtypes of the DataAccessException class.
 */



public class JOOQToSpringExceptionTranslator extends DefaultExecuteListener {
    public void exception(ExecuteContext context) {
        SQLDialect dialect = context.configuration().dialect();
        SQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator(dialect.name());
        context.exception(translator.translate("Access database using jOOQ", context.sql(), context.sqlException()));
    }
}
