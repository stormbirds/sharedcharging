package cn.stormbirds.sharedcharging.common.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * <p>
 * cn.stormbirds.sharedcharging.common.utils
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/9/28 上午2:15
 */
public class DateTimeUtils {

    /**
     * JDK8 LocalDateTime 转换 Date
     * @param time
     * @return
     */
    public static Date localDateTime2DateTime(LocalDateTime time){
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);

        return Date.from(zdt.toInstant());
    }
}
