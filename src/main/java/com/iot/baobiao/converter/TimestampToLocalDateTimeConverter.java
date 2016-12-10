package com.iot.baobiao.converter;

import org.jooq.Converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * Created by jia on 2016/11/25.
 */
public class TimestampToLocalDateTimeConverter implements Converter<Timestamp, LocalDateTime> {
    @Override
    public LocalDateTime from(Timestamp timestamp) {
        //防止数据库中的该项数据为null值
        if (timestamp == null) return null;
        return timestamp.toLocalDateTime();
    }

    @Override
    public Timestamp to(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return Timestamp.from(localDateTime.toInstant(ZoneOffset.ofHours(8)));
    }

    @Override
    public Class<Timestamp> fromType() {
        return Timestamp.class;
    }

    @Override
    public Class<LocalDateTime> toType() {
        return LocalDateTime.class;
    }
}
