package com.openclassrooms.realestatemanager;

import org.junit.Test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    // Conversions Dollars <> Euros
    @Test
    public void convertDollarToEuro_withSuccess() {
        assertEquals(81,Utils.convertDollarToEuro(100));
    }

    @Test
    public void convertEuroToDollar_withSuccess() {
        assertEquals(102,Utils.convertEurosToDollars(100));
    }


    @Test
    public void convertDate_withSuccess() throws ParseException {
        //Pattern  dd/MM/yyyy [27/07/2022]
        Pattern datePattern = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");
        Matcher matcher = datePattern.matcher(Utils.getTodayDate());
        assertTrue(matcher.matches());

        //Pattern  MM/dd/yyyy [07/27/2022]
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date date = sdf.parse("03/29/2018");

        Pattern usDatePattern = Pattern.compile("^(0[1-9]|1[012])/(0[1-9]|[12][0-9]|3[01])/\\d{4}$");
        Matcher scdMatcher = usDatePattern.matcher(Utils.getFormattedDate(date, "MM/dd/yyyy"));
        assertTrue(scdMatcher.matches());
    }
}