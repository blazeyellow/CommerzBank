package com.commerzbank.pojo;

import com.commerzbank.exception.InvalidDateException;

public class MyDate implements Date {

    private Integer dateDay;
    private Integer dateMonth;
    private Integer dateYear;

    private static int [] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    // As we have no access to any Date library to get the current century, I am hardcoding the century here
    public static Integer century = 2000;

    public MyDate(String date) throws InvalidDateException {
        String [] mmddyy = date.split("/");

        if (mmddyy.length!=3) {
            throw new InvalidDateException("Date is invalid");

        }

        dateYear = validateYear(mmddyy[2]);
        dateMonth = validateMonth(mmddyy[1]);
        dateDay = validateDay(mmddyy[0]);

    }

    public Integer getDateDay() {
        return dateDay;
    }

    public Integer getDateMonth() {
        return dateMonth;
    }

    public Integer getDateYear() {
        return dateYear;
    }

    private Integer validateDay(String day) throws InvalidDateException {
        Integer dayInt = null;
        try {
            dayInt = Integer.parseInt(day);

        } catch (NumberFormatException e) {

        }

        if (dayInt==null) {
            throw new InvalidDateException("Day is not a number");

        }

        if (dateYear==null || dateMonth==null) {
            throw new InvalidDateException("Year and/or month not set, cannot validate day");

        }

        int maxDays = daysInMonth[dateMonth-1];
        if (dateMonth==2 && isLeapYear(dateYear)) {
            maxDays++;

        }

        if (dayInt<1 || dayInt>maxDays) {
            throw new InvalidDateException("Day is out of range");

        }

        return dayInt;

    }

    private Integer validateMonth(String month) throws InvalidDateException {

        Integer monthInt = null;
        try {
            monthInt = Integer.parseInt(month);

        } catch (NumberFormatException e) {

        }

        if (monthInt==null) {
            throw new InvalidDateException("Month is not a number");

        }

        if (monthInt>12 || monthInt<1) {
            throw new InvalidDateException("Month is out of range");

        }

        return monthInt;

    }

    private Integer validateYear(String year) throws InvalidDateException {

        Integer yearInt = null;
        try {
            yearInt = Integer.parseInt(year);

        } catch (NumberFormatException e) {

        }

        if (yearInt==null) {
            throw new InvalidDateException("Year is not a number");

        }

        if (yearInt<0) {
            throw new InvalidDateException("Year is negative");

        }

        if (yearInt<100) {
            return century + yearInt;

        }

        return yearInt;
    }

    public static boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;

        } else if (year % 400 == 0) {
            return true;

        } else if (year % 100 == 0) {
            return false;

        } else {
            return true;

        }
    }

    public static Long diff(MyDate date1, MyDate date2) {
        int totalDays = 0;
        int firstMonth = 0;
        int lastMonth = 0;
        int startMonth = date1.getDateMonth();
        for (int yearCount = date1.getDateYear(); yearCount<=date2.getDateYear(); yearCount++) {
            for (int monthCount = startMonth; monthCount<=12; monthCount++) {
                // very first month could be a part month and therefore have any number of days
                if (firstMonth==0) {

                    // the span maybe within one calendar month, subtract date2 days from date1 days to get total and continue
                    if (date1.getDateMonth().equals(date2.getDateMonth()) && date1.getDateYear().equals(date2.getDateYear())) {
                        totalDays = date2.getDateDay() - date1.getDateDay();
                        break;

                    }

                    // add the number of days in part month
                    firstMonth = getNoOfDays(yearCount, monthCount) - date1.getDateDay();
                    totalDays = totalDays + firstMonth;
                    continue;

                }

                // are we at the last month? add number of days for this part month
                if (yearCount==date2.getDateYear() && monthCount==date2.getDateMonth()) {
                    lastMonth = date2.getDateDay();
                    totalDays = totalDays + lastMonth;
                    break;

                }

                // add the total days in this month to total
                totalDays = totalDays + getNoOfDays(yearCount, monthCount);

                // reset month count if we have got to the end of the year
                if (monthCount==12) {
                    startMonth=1;

                }
            }
        }

        return Long.valueOf(totalDays);

    }

    /**
     *
     * return the total no. of days for a given month in a year, taking into account a leap year
     *
     * @param year
     * @param month
     * @return
     */
    private static int getNoOfDays(int year, int month) {
        int total = daysInMonth[month-1];
        if (isLeapYear(year) && month==2) {
            total++;

        }

        return total;

    }

    public boolean isBefore(MyDate date) {
        if (this.dateYear<date.getDateYear()) {
            return true;

        }

        if (this.dateYear.equals(date.getDateYear()) && this.dateMonth<date.getDateMonth()) {
            return true;

        }

        if (this.dateYear.equals(date.getDateYear()) && this.dateMonth.equals(date.getDateMonth()) && this.dateDay<date.getDateDay()) {
            return true;

        }

        return false;

    }
}
