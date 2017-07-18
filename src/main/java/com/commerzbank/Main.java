package com.commerzbank;

import com.commerzbank.exception.InvalidDateException;
import com.commerzbank.pojo.MyDate;

public class Main {

    /**
     *
     * assemble jar using assembly:assembly plugin. this creates Commerzbank-1.0-SNAPSHOT-jar-with-dependencies.jar
     *
     * from command line: java -jar Commerzbank-1.0-SNAPSHOT-jar-with-dependencies.jar <fromDate> <toDate>
     *
     * @param args
     */
    public static void main(String[] args) {

        if (args.length!=2) {
            System.out.println("Usage: calcDateTool fromDate toDate");
            return;

        }

        MyDate dateFrom = null;
        try {
            dateFrom = new MyDate(args[0]);

        } catch (InvalidDateException e) {
            System.out.println("fromDate: " + e.getMessage());
            return;

        }

        MyDate dateTo = null;
        try {
            dateTo = new MyDate(args[1]);

        } catch (InvalidDateException e) {
            System.out.println("toDate: " + e.getMessage());
            return;

        }

        System.out.println("Total number of days difference = " + MyDate.diff(dateFrom, dateTo));

    }
}
