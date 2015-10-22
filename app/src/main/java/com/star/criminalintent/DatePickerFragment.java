package com.star.criminalintent;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatePickerFragment extends PickerFragment {

    private DatePicker mDatePicker;
    private Button mDatePickerButton;

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(args);

        return datePickerFragment;
    }

    @Override
    protected View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_date_picker, container, false);
    }

    @Override
    protected int getPickerId() {
        return R.id.date_picker;
    }

    @Override
    protected void setPickerButtonOnClickListener(final Calendar calendar, View view) {
        mDatePickerButton = (Button) view.findViewById(R.id.date_picker_button);
        mDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = getDate(calendar);
                sendResult(Activity.RESULT_OK, date);
            }
        });
    }

    @Override
    protected View getDialogView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.dialog_date, null);
    }

    @Override
    protected int getDialogPickerId() {
        return R.id.dialog_date_date_picker;
    }

    @Override
    protected int getPickerTitle() {
        return R.string.date_picker_title;
    }

    @Override
    protected void setDate(Calendar calendar, View view, int pickerId) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        mDatePicker = (DatePicker) view.findViewById(pickerId);
        mDatePicker.init(year, month, dayOfMonth, null);
    }

    @NonNull
    @Override
    protected Date getDate(Calendar calendar) {
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int dayOfMonth = mDatePicker.getDayOfMonth();

        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new GregorianCalendar(
                year, month, dayOfMonth, hourOfDay, minute).getTime();
    }

}
