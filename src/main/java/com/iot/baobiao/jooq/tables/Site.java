/**
 * This class is generated by jOOQ
 */
package com.iot.baobiao.jooq.tables;


import com.iot.baobiao.converter.TimestampToLocalDateTimeConverter;
import com.iot.baobiao.jooq.Keys;
import com.iot.baobiao.jooq.Nutch;
import com.iot.baobiao.jooq.tables.records.SiteRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class Site extends TableImpl<SiteRecord> {

    private static final long serialVersionUID = -1073683840;

    /**
     * The reference instance of <code>nutch.site</code>
     */
    public static final Site SITE = new Site();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SiteRecord> getRecordType() {
        return SiteRecord.class;
    }

    /**
     * The column <code>nutch.site.id</code>.
     */
    public final TableField<SiteRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>nutch.site.domain</code>.
     */
    public final TableField<SiteRecord, String> DOMAIN = createField("domain", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>nutch.site.start_url</code>.
     */
    public final TableField<SiteRecord, String> START_URL = createField("start_url", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>nutch.site.sitename</code>.
     */
    public final TableField<SiteRecord, String> SITENAME = createField("sitename", org.jooq.impl.SQLDataType.VARCHAR.length(45), this, "");

    /**
     * The column <code>nutch.site.fetch_time</code>.
     */
    public final TableField<SiteRecord, LocalDateTime> FETCH_TIME = createField("fetch_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "", new TimestampToLocalDateTimeConverter());

    /**
     * Create a <code>nutch.site</code> table reference
     */
    public Site() {
        this("site", null);
    }

    /**
     * Create an aliased <code>nutch.site</code> table reference
     */
    public Site(String alias) {
        this(alias, SITE);
    }

    private Site(String alias, Table<SiteRecord> aliased) {
        this(alias, aliased, null);
    }

    private Site(String alias, Table<SiteRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Nutch.NUTCH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<SiteRecord, Integer> getIdentity() {
        return Keys.IDENTITY_SITE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<SiteRecord> getPrimaryKey() {
        return Keys.KEY_SITE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SiteRecord>> getKeys() {
        return Arrays.<UniqueKey<SiteRecord>>asList(Keys.KEY_SITE_PRIMARY, Keys.KEY_SITE_ID_UNIQUE, Keys.KEY_SITE_DOMAIN_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Site as(String alias) {
        return new Site(alias, this);
    }

    /**
     * Rename this table
     */
    public Site rename(String name) {
        return new Site(name, null);
    }
}
