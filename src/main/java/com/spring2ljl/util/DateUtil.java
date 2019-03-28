package com.spring2ljl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String format(Long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(time));
    }
}
