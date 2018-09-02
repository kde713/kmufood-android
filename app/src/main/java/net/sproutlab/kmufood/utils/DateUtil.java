package net.sproutlab.kmufood.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

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

    public static class WeekRange {
        private final Date startDate;
        private final Date endDate;

        public WeekRange() {
            Calendar startCal = Calendar.getInstance();
            startCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            this.startDate = startCal.getTime();

            Calendar endCal = (Calendar) startCal.clone();
            endCal.add(Calendar.DATE, 6);
            this.endDate = endCal.getTime();
        }

        public Date getStartDate() {
            return startDate;
        }

        public Date getEndDate() {
            return endDate;
        }
    }
}
