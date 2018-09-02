package net.sproutlab.kmufood.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String getStringFromDate(Date date) {
        return dateFormat.format(date);
    }

    public static Date getDateFromString(String dateString) throws ParseException {
        return dateFormat.parse(dateString);
    }

    public static Date addDaysToDate(Date orgDate, int addDays) {
        Calendar c = Calendar.getInstance();
        c.setTime(orgDate);
        c.add(Calendar.DATE, addDays);
        return c.getTime();
    }
}
