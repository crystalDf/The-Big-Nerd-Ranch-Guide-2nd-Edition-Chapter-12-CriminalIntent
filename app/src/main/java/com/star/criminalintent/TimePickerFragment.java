package com.star.criminalintent;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TimePickerFragment extends PickerFragment {

    private TimePicker mTimePicker;
    private Button mTimePickerButton;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setArguments(args);

        return timePickerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_picker, container, false);

        mTimePicker = (TimePicker) view.findViewById(R.id.time_picker);
        setDate();

        mTimePickerButton = (Button) view.findViewById(R.id.time_picker_button);
        mTimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(Activity.RESULT_OK, getDate());
            }
        });

        return view;
    }

    @Override
    protected void setDate() {
        int hourOfDay = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);

        mTimePicker.setIs24HourView(true);
        mTimePicker.setCurrentHour(hourOfDay);
        mTimePicker.setCurrentMinute(minute);
    }

    @Override
    protected Date getDate() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);

        int hourOfDay = mTimePicker.getCurrentHour();
        int minute = mTimePicker.getCurrentMinute();

        return new GregorianCalendar(
                year, month, dayOfMonth, hourOfDay, minute).getTime();
    }

}
