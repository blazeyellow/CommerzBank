package com.commerzbank.pojo;

import com.commerzbank.exception.InvalidDateException;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Calendar;

import static org.hamcrest.MatcherAssert.assertThat;

public class MyDateTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testCreateDate_valid_ddmmyy() {
        MyDate myDate = new MyDate("15/10/2008");

        assertThat(myDate, CoreMatchers.notNullValue());

    }

    @Test
    public void testCreateDate_valid_4digityear() {
        MyDate myDate = new MyDate("15/10/2008");

        assertThat(myDate, CoreMatchers.notNullValue());

    }

    @Test
    public void testCreateDate_valid_1digityear() {
        MyDate myDate = new MyDate("15/10/8");

        assertThat(myDate, CoreMatchers.notNullValue());

    }

    @Test
    public void testCreateDate_valid_2digityear() {
        MyDate myDate = new MyDate("15/10/08");

        assertThat(myDate, CoreMatchers.notNullValue());

    }

    @Test
    public void testCreateDate_year_negative() {
        expectedException.expect(InvalidDateException.class);
        expectedException.expectMessage("Year is negative");

        MyDate myDate = new MyDate("15/10/-1");

    }

    @Test
    public void testCreateDate_year_nonnumeric() {
        expectedException.expect(InvalidDateException.class);
        expectedException.expectMessage("Year is not a number");

        MyDate myDate = new MyDate("15/10/A");

    }

    @Test
    public void testCreateDate_month_outofrange_toohigh() {
        expectedException.expect(InvalidDateException.class);
        expectedException.expectMessage("Month is out of range");

        MyDate myDate = new MyDate("15/13/2008");

    }

    @Test
    public void testCreateDate_month_outofrange_toolow() {
        expectedException.expect(InvalidDateException.class);
        expectedException.expectMessage("Month is out of range");

        MyDate myDate = new MyDate("15/0/2008");

    }

    @Test
    public void testCreateDate_month_nonnumeric() {
        expectedException.expect(InvalidDateException.class);
        expectedException.expectMessage("Month is not a number");

        MyDate myDate = new MyDate("15/A/2008");

    }

    @Test
    public void testCreateDate_day_nonnumeric() {
        expectedException.expect(InvalidDateException.class);
        expectedException.expectMessage("Day is not a number");

        MyDate myDate = new MyDate("A/10/2008");

    }

    @Test
    public void testCreateDate_day_outofrange_toolow() {
        expectedException.expect(InvalidDateException.class);
        expectedException.expectMessage("Day is out of range");

        MyDate myDate = new MyDate("0/10/2008");

    }

    @Test
    public void testCreateDate_day_outofrange_toohigh_feb_leapyear() {
        expectedException.expect(InvalidDateException.class);
        expectedException.expectMessage("Day is out of range");

        MyDate myDate = new MyDate("30/2/2008");

    }

    @Test
    public void testCreateDate_day_outofrange_ok_feb_leapyear() {
        MyDate myDate = new MyDate("29/2/2008");

        assertThat(myDate, CoreMatchers.notNullValue());

    }

    @Test
    public void testCreateDate_day_outofrange_toohigh_feb_nonleapyear() {
        expectedException.expect(InvalidDateException.class);
        expectedException.expectMessage("Day is out of range");

        MyDate myDate = new MyDate("29/2/2009");

    }

    @Test
    public void testCreateDate_day_outofrange_ok_feb_nonleapyear() {
        MyDate myDate = new MyDate("28/2/2009");

        assertThat(myDate, CoreMatchers.notNullValue());

    }

    @Test
    public void testIsBefore_true() {
        MyDate myDate1 = new MyDate("28/2/2009");
        MyDate myDate2 = new MyDate("28/3/2009");

        assertThat(myDate1.isBefore(myDate2), CoreMatchers.equalTo(true));

    }

    @Test
    public void testIsBefore_false() {
        MyDate myDate1 = new MyDate("28/3/2009");
        MyDate myDate2 = new MyDate("28/2/2009");

        assertThat(myDate1.isBefore(myDate2), CoreMatchers.equalTo(false));

    }

    @Test
    public void testDateDiff_leap_year_with_span_over_feb_29() throws Exception {

        // setup
        String date1 = "24/02/2012";
        String date2 = "01/03/2012";

        MyDate myDate1 = new MyDate(date1);
        MyDate myDate2 = new MyDate(date2);

        // assert
        assertThat(MyDate.diff(myDate1, myDate2), CoreMatchers.equalTo(6L));

    }

    @Test
    public void testDateDiff_leap_year_with_no_span_over_feb_29() throws Exception {

        // setup
        String date1 = "24/02/2012";
        String date2 = "28/02/2012";

        MyDate myDate1 = new MyDate(date1);
        MyDate myDate2 = new MyDate(date2);

        // assert
        assertThat(MyDate.diff(myDate1, myDate2), CoreMatchers.equalTo(4L));

    }

    @Test
    public void testDateDiff_leap_year_with_span_over_4x_feb_29() throws Exception {

        // setup
        String date1 = "24/02/2008";
        String date2 = "1/03/2020";

        MyDate myDate1 = new MyDate(date1);
        MyDate myDate2 = new MyDate(date2);

        // assert
        // 12 * 365 + 4 leap year days + 4 days before 1st lyd + 1st March 2020
        assertThat(MyDate.diff(myDate1, myDate2), CoreMatchers.equalTo(4389L));

    }

    @Test
    public void testDateDiff() throws Exception {

        // setup
        String date1 = "15/06/2013";
        String date2 = "30/06/2013";

        MyDate myDate1 = new MyDate(date1);
        MyDate myDate2 = new MyDate(date2);

        // assert
        assertThat(MyDate.diff(myDate1, myDate2), CoreMatchers.equalTo(15L));

    }
}
