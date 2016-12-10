package com.iot.baobiao.executelistener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultExecuteListener;

/**
 * Created by jia on 2016/10/31.
 */
public class LoggingExecuteListener extends DefaultExecuteListener {

    private static Logger logger = Logger.getLogger("JOOQExecutionListener");

    private final DSLContext create;

    public LoggingExecuteListener(SQLDialect dialect) {
        create = DSL.using(dialect);
    }

    @Override
    public void executeStart(ExecuteContext ctx) {

        String statement = null;

        if (ctx.query() != null) {
            statement = create.renderInlined(ctx.query());
        } else if (ctx.routine() != null) {
            statement = create.renderInlined(ctx.routine());
        } else if (!StringUtils.isBlank(ctx.sql())) {
            statement = ctx.sql();
        }

        if (statement != null) {
            logger.info(statement);
        }
    }
}
