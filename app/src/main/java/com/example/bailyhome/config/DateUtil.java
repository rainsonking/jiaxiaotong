package com.example.bailyhome.config;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/8 0008.
 */
public class DateUtil {
    public static Date stringToDate(String dateString) {
        ParsePosition position = new ParsePosition(0);
        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //Date dateValue = simpleDateFormat.parse(dateString);
        Date dateValue = simpleDateFormat.parse(dateString, position);
        return dateValue;
    }
}
