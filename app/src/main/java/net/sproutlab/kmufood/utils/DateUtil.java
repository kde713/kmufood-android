package net.sproutlab.kmufood.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String getStringFromDate(Date date) {
        return dateFormat.format(date);
    }

    public static Date getDateFromString(String dateString) throws ParseException {
        return dateFormat.parse(dateString);
    }
}
