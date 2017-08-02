package com.mall.b2bp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class DateUtils {
    private final static Logger LOG = Logger.getLogger(DateUtils.class);

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_3 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_2 = "yyyyMMdd";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_5 = "MM/dd/yyyy";
    public static final String DATE_FORMATE_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMATE_YYYYMMDDHHMI = "yyyyMMddHHmm";

    public static final String DATE_FORMAT_6 = "MMddyyyy";
    
    public static final String DATE_TIME_FORMATE_YYYMMDD_HHMMSS = "yyyyMMddHHmmss";

    public static final String DATE_FORMAT_7 = "yyyy/MM/dd HH:mm:ss";
    //Format: YYYY-MM-DDTHH24:MI:SS
    public static final String DATE_FORMAT_8 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_9= "yyyy/MM/dd";

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);


    public static Date addOneMonth(Date now, int month) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.MONTH, month);
            return calendar.getTime();
        } catch (Exception e) {
            LOG.error("addOneMonth Error" + e.getMessage(), e);
            return null;
        }
    }


    public static Date addOneDay(Date time, int day) {
        try {
            Calendar begin = Calendar.getInstance();
            begin.setTime(time);
            begin.add(Calendar.DAY_OF_MONTH, day);
            return begin.getTime();
        } catch (Exception e) {
            LOG.error("addOndDay Error" + e.getMessage(), e);
        }
        return null;
    }




    public static Date parseDateStr(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            if (StringUtils.isNotEmpty(dateStr)) {
                return sdf.parse(dateStr);
            } else {
                return null;
            }
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }
    
    public static String parseDateStrStr(String dateStr, String formatIn, String formatOut) {
    	SimpleDateFormat sdfIn = new SimpleDateFormat(formatIn);
        SimpleDateFormat sdfOut = new SimpleDateFormat(formatOut);
    	try {
    		if (StringUtils.isNotEmpty(dateStr)) {
    			return sdfOut.format(sdfIn.parse(dateStr));
    		} else {
    			return null;
    		}
    	} catch (ParseException e) {
    		LOG.error(e.getMessage(), e);
    	}
    	return null;
    }

    public static String formatDate(final Date date, String format) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }
    }

    public static String formatDate(final Date date, String format, Locale locale) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
            return sdf.format(date);
        }
    }

    public static String formatDate(final Date date) {
        return formatDate(date, DATE_FORMAT_2);
    }
    
    public static String formatDateTime(final Date date) {
        return formatDate(date, DATE_TIME_FORMAT);
    }

    public static Date getCurrentDate() {
        Date d = new Date();

        String dStr = formatDate(d, DATE_FORMAT_3);
        return parseDateStr(dStr, DATE_FORMAT_3);

    }

    public static String getCurrentFormatDate(String format) {
        Date d = new Date();
        return formatDate(d, format);
    }
    
    public static Date getCurrentDateYYYYMMDD() {
        Date d = new Date();

        String dStr = formatDate(d, DATE_FORMAT);
        return parseDateStr(dStr, DATE_FORMAT);

    }


    public static boolean compareDate(Date c1, Date c2) {


        if (c1 != null && c2 != null) {

            Long l1 = Long.valueOf(formatDate(c1, DATE_FORMATE_YYYYMMDD));

            Long l2 = Long.valueOf(formatDate(c2, DATE_FORMATE_YYYYMMDD));

            if (l1 != null && l2 != null) {
                if (l1 >= l2)
                    return true;
                else
                    return false;
            }
        }

        return false;

    }


    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }
    public static boolean isValidDate(String str) {
    	        boolean convertSuccess=true;
    	        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_6);
    	         try {
    	            format.parse(str);
    	         } catch (ParseException e) {
    	            convertSuccess=false;
    	        } 
    	        return convertSuccess;
    	 }
    
    public static void main(String[] args) {
    	
    	System.out.println(parseDateStr("5022016",DATE_FORMAT_6));;
	}
}
