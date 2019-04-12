package com.dataexchange.server.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @Auther: Administrator
 * @Date: 2018-12-28 10:27
 * @Description:
 */
public class DateUtils {

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String parseDate(Timestamp timestamp){
        if(timestamp == null)
            return null;
        return df.format(timestamp);
    }
}
