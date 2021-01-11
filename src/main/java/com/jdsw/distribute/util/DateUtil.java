package com.jdsw.distribute.util;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil extends org.apache.commons.lang3.time.DateUtils{
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     * @return
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     * @return
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }
    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null){
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/(24*60*60*1000);
    }

    /**
     * 获取过去的小时
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/(60*60*1000);
    }

    /**
     * 获取过去的分钟
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/(60*1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis){
        long day = timeMillis/(24*60*60*1000);
        long hour = (timeMillis/(60*60*1000)-day*24);
        long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
        long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
        long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
        return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }


    /**
     * 计算两个日期相差秒数
     * @param
     * @return date2
     * @throws
     */
    public static int DvalueSeconds(Date endDate, Date startDate) {
        long endSecond = endDate.getTime();
        long startSecond = startDate.getTime();
        int timeDiff = (int)((endSecond - startSecond) / 1000);

        return timeDiff;

    }

    public static String getMinutesCount(Date startDate, Date endDate) {
        // 根据起始日期计算起始的毫秒
        long startTime = startDate.getTime();
        // 根据终止日期计算终止的毫秒
        long endTime = endDate.getTime();
        // 通过起始毫秒和终止毫秒的差值，计分钟
        long diff = endTime - startTime;
        long day = diff / 86400000;
        long hour= diff % 86400000 / 3600000;//以小时为单位取整
        long min = diff % 86400000 % 3600000 / 60000;//以分钟为单位取整
        long seconds = diff % 86400000 % 3600000 % 60000 / 1000;//以秒为单位取整a

        //return hour+(day*24)+"小时"+min+"分"+seconds+"秒";
        return min+"分"+seconds+"秒";

    }
    public static String getMinutesCount2(Date startDate, Date endDate) {
        // 根据起始日期计算起始的毫秒
        long startTime = startDate.getTime();
        // 根据终止日期计算终止的毫秒
        long endTime = endDate.getTime();
        // 通过起始毫秒和终止毫秒的差值，计分钟
        long diff = startTime - endTime;
        long day = diff / 86400000;
        long hour= diff % 86400000 / 3600000;//以小时为单位取整
        long min = diff % 86400000 % 3600000 / 60000;//以分钟为单位取整
        long seconds = diff % 86400000 % 3600000 % 60000 / 1000;//以秒为单位取整a

        //return hour+(day*24)+"小时"+min+"分"+seconds+"秒";
        return min+"分"+seconds+"秒";

    }

    /**
     *
     * @Description:获取当月第一天的时间
     * @Since:2016年12月13日 下午2:12:50
     * @Author:ljz
     */
    public static String firstDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH,1);
        return format.format(c.getTime()) + " 00:00:00";
    }

    /**
     * 获取当天的时间
     * @author: ljz
     * @date: 2018年4月17日 上午11:09:37
     */
    public static String curDate_0(){
        return formatDate(new Date(), "yyyy-MM-dd 00:00:00");
    }

    /**
     *
     * @Description:获取当天的时间
     * @Since:2016年12月13日 下午2:12:50
     * @Author:ljz
     */
    public static String curDate(){
        return formatDate(new Date(), "yyyy-MM-dd 23:59:59");
    }

    /**
     *
     * @Description:获取当月最后一天的时间
     * @Since:2016年12月13日 下午2:12:50
     * @Author:ljz
     */
    public static String lastDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前月最后一天
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,
                c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(c.getTime()) + " 23:59:59";
    }

    /**
     * 获取当前时间的24小时后的时间
     * @return
     */
    public static String getNextDay(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date().getTime()+1*24*3600*1000);
    }

    /**
     * 获取当前时间3天后的时间
     * @return
     */
    public static String getSanDay(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date().getTime()+1*72*3600*1000);
    }

    /**
     * 获取多少毫秒后的时间
     * @param time
     * @return
     */
    public static String getOverTime(Integer time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        Date afterDate = new Date(now.getTime() + time);
        return sdf.format(afterDate);

    }
    public static Boolean getOvertimeBoo(String parse) throws Exception {
        if(StringUtils.isEmpty(parse)){
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        Date start = sdf.parse(parse);
        Date end = new Date(now.getTime());
        long cha = start.getTime() - end.getTime();
        System.out.println(cha);
        if(cha <=300000 && cha > 0){
            return true;
        }else{
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        Date start = sdf.parse("2021-01-11 16:20:00");
        System.out.println("超时"+getMinutesCount2(now,start));
        System.out.println(start.getTime()-now.getTime());

    }

}
