package com.darcos.julie.mynews.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.darcos.julie.mynews.Activities.SearchActivity;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    //when user change date change value of button
    public void onDateSet(DatePicker view, int year, int month, int day) {

        if (getTag().equals("begin")) {

            String y = Integer.toString(year);
            String m = Integer.toString(month + 1);
            String d = Integer.toString(day);

            if (d.length() == 1) {
                d = "0" + d;
            }
            if (m.length() == 1) {
                m = "0" + m;
            }

            SearchActivity.setBeginDate(d + "/" + m + "/" + y);
        }

        if (getTag().equals("end")) {
            String y = Integer.toString(year);
            String m = Integer.toString(month + 1);
            String d = Integer.toString(day);
            if (d.length() == 1) {
                d = "0" + d;
            }
            if (m.length() == 1) {
                m = "0" + m;
            }
            SearchActivity.setEndDate(d + "/" + m + "/" + y);
        }
    }
}