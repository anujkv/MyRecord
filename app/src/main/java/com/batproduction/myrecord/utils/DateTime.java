package com.batproduction.myrecord.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTime {
    Calendar c = Calendar.getInstance();
    String formattedDate;
    public String currentDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public String currentDay() {
        Date date = c.getTime();
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
    }

    public String startDateOfCurrentMonth(){
        SimpleDateFormat sd = new SimpleDateFormat("01-MMM-yyyy");
        formattedDate = sd.format(c.getTime());
        return formattedDate;
    }

    public String currentDate(){
        SimpleDateFormat sd = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = sd.format(c.getTime());
        return formattedDate;
    }


    public String convertDate_dd_MMM_yyyy(String date){
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        DateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.getDefault());
        Date date1 = null;
        String finaldate;
        try {
            date1 = inputFormat.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        finaldate = outputFormat.format(date1);
        if (date1 != null) {
            Log.e("Date1 : ",date1.toString());
        }
        Log.e("FinalOutput : ",finaldate);

        return finaldate;
    }

    public String dateConversionDDMMMYYYY(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = dateFormat.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM,yyyy");
        String finalFromDate = timeFormat.format(fromDate);
        return finalFromDate;
    }

    public String convert_date(String date) {
        SimpleDateFormat newformat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat oldformat = new SimpleDateFormat("dd-MM-yyyy");

        Date myDate;
        try {
//            int month = mMonth + 1;
            myDate = oldformat.parse(date);
            date = newformat.format(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String dateConDDMMYYYY(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = dateFormat.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
            try {
                fromDate = timeFormat.parse(date);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }

        return timeFormat.format(fromDate);
    }

    public Date timestamp(String s){
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy, hh:mm");
        Date date = null;
        try {
            date = (Date)formatter.parse(s);
            if (date != null) {
                System.out.println("Today is " +date.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
