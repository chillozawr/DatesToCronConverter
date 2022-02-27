package com.zhidkov.viktor;

import com.digdes.school.DatesToCronConvertException;
import com.digdes.school.DatesToCronConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class DatesToCron implements DatesToCronConverter {

    @Override
    public String convert(List<String> dates) throws DatesToCronConvertException {
        List<GregorianCalendar> datetimeList = new ArrayList<>();
        SimpleDateFormat formater = new SimpleDateFormat(DATE_FORMAT);
        formater.setLenient(false);
        TreeSet<Integer> seconds = new TreeSet<>();
        TreeSet<Integer> minutes = new TreeSet<>();
        TreeSet<Integer> hours = new TreeSet<>();
        TreeSet<Integer> days = new TreeSet<>();
        TreeSet<Integer> months = new TreeSet<>();
        TreeSet<Integer> daysOfWeek = new TreeSet<>();
        TreeSet<Integer> years = new TreeSet<>();
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        for (String date : dates) {
            Date date1 = null;
            try {
                date1 = formater.parse(date);
            } catch (ParseException e) {
                throw new DatesToCronConvertException();
            }

            assert date1 != null;
            calendar.setTime(date1);
            seconds.add(calendar.get(Calendar.SECOND));
            minutes.add(calendar.get(Calendar.MINUTE));
            hours.add(calendar.get(Calendar.HOUR_OF_DAY));
            days.add(calendar.get(Calendar.DAY_OF_MONTH));
            months.add(calendar.get(Calendar.MONTH) + 1);
            daysOfWeek.add(calendar.get(Calendar.DAY_OF_WEEK) - 1);
            years.add(calendar.get(Calendar.YEAR));
        }
        String cronExp = "";
        int datesSize = dates.size();
        //*************************************************************************************************************
        // Работает с секундами
        if (seconds.size() == 1) {
            if (seconds.contains(0)) {
                cronExp += "0 ";
            }
            else {
                cronExp += "*/" + seconds.first() + " ";
            }
        }
        //*************************************************************************************************************
        // Работаем с минутами
        if (minutes.size() == 1 && minutes.contains(0)) {
            cronExp += "0 ";
        }
        else if (minutes.size() == 2) {
            cronExp += minutes.first() + "/" + minutes.last() + " ";
        }
        else {
            cronExp += "* ";
        }
        //*************************************************************************************************************
        // Работаем с часами
        if (hours.size() == 1) {
            if (hours.contains(0)) {
                cronExp += "0 ";
            }
            else {
                cronExp += hours.first() + " ";
            }
        }
        else if (days.size() > 1){
            cronExp += hours.first() + "-" + hours.last() + " ";
        }
        else {
            cronExp += "* ";
        }
        //*************************************************************************************************************
        // Работает с днями
        if (days.size() == 1 && months.size() > 1) {
            cronExp += days.first() + " ";
        }
        else {
            cronExp += "* ";
        }
        //*************************************************************************************************************
        // Работает с месяцами
        if (months.size() == 1 && years.size() > 1) {
            cronExp += months.last() + " ";
        }
        else {
            cronExp += "* ";
        }
        //*************************************************************************************************************
        // Работает с днями недели
        if (daysOfWeek.size() == 1) {
            cronExp += dayToString(daysOfWeek.first());
        }
        else if (daysOfWeek.size() > 1){
            cronExp += "* ";
        }
        else if (days.size() > 7){
            cronExp += dayToString(daysOfWeek.first()) + "-" + dayToString(daysOfWeek.last());
        }
        else {
            throw new DatesToCronConvertException();
        }

        return cronExp;
    }

    @Override
    public String getImplementationInfo() {
        return "Жидков Виктор Петрович " + getClass().getSimpleName() + " " + getClass().getPackage().getName()
                + " " + "https://github.com/chillozawr/DatesToCronConverter";
    }

    private String dayToString(int day) {
        String result = null;
        switch (day) {
            case 1:
                result = "MON";
                break;
            case 2:
                result = "TUE";
                break;
            case 3:
                result = "WED";
                break;
            case 4:
                result = "THU";
                break;
            case 5:
                result = "FRI";
                break;
            case 6:
                result = "SUT";
                break;
            case 7:
                result = "SUN";
                break;
            default:
                break;
        }
        return result;
    }


    public static void main(String[] args) throws DatesToCronConvertException {
        List<String> dateList = new ArrayList<>();
        List<String> dateList1 = new ArrayList<>();
        dateList.add("2022-01-25T08:00:00");
        dateList.add("2022-01-25T08:30:00");
        dateList.add("2022-01-25T09:00:00");
        dateList.add("2022-01-25T09:30:00");
        dateList.add("2022-01-26T08:00:00");
        dateList.add("2022-01-26T08:30:00");
        dateList.add("2022-01-26T09:00:00");
        dateList.add("2022-01-26T09:00:00");

        dateList1.add("2022-01-24T19:53:00");
        dateList1.add("2022-01-24T19:54:00");
        dateList1.add("2022-01-24T19:55:00");
        dateList1.add("2022-01-24T19:56:00");
        dateList1.add("2022-01-24T19:57:00");
        dateList1.add("2022-01-24T19:58:00");
        dateList1.add("2022-01-24T19:59:00");
        dateList1.add("2022-01-24T20:00:00");
        dateList1.add("2022-01-24T20:01:00");
        dateList1.add("2022-01-24T20:02:00");


        DatesToCron dada = new DatesToCron();

        System.out.println("cron = " + dada.convert(dateList));
        System.out.println("cron = " + dada.convert(dateList1));
    }
}


