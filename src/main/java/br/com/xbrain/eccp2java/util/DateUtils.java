package br.com.xbrain.eccp2java.util;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {

    @SuppressWarnings("checkstyle:ParameterNumber")
    public static Date createDate(int year, int month, int day, int hourOfTheDay, int minute, int second, int milis) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hourOfTheDay, minute, second);
        calendar.set(Calendar.MILLISECOND, milis);
        return calendar.getTime();
    }

    public static Time createTime(int hourOfTheDay, int minute, int second, int milis) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, hourOfTheDay, minute, second);
        calendar.set(Calendar.MILLISECOND, milis);
        return new Time(calendar.getTime().getTime());
    }
}
