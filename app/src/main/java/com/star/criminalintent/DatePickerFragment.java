package com.star.criminalintent;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatePickerFragment extends DialogFragment {

    private static final String ARG_DATE = "date";

    public static final String EXTRA_DATE = "date";

    private DatePicker mDatePicker;
    private Button mDatePickerButton;

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(args);

        return datePickerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT){
            View view = inflater.inflate(R.layout.fragment_date_picker,
                    container, false);

            Date date = (Date) getArguments().getSerializable(ARG_DATE);

            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            mDatePicker = (DatePicker) view.findViewById(R.id.date_picker);
            mDatePicker.init(year, month, dayOfMonth, null);

            mDatePickerButton = (Button) view.findViewById(R.id.date_picker_button);
            mDatePickerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int year = mDatePicker.getYear();
                    int month = mDatePicker.getMonth();
                    int dayOfMonth = mDatePicker.getDayOfMonth();

                    int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);

                    Date date = new GregorianCalendar(
                            year, month, dayOfMonth, hourOfDay, minute).getTime();

                    sendResult(Activity.RESULT_OK, date);
                }
            });

            return view;
        }

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_date, null);

        mDatePicker = (DatePicker) view.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, dayOfMonth, null);

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int dayOfMonth = mDatePicker.getDayOfMonth();

                        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        Date date = new GregorianCalendar(
                                year, month, dayOfMonth, hourOfDay, minute).getTime();

                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, Date date) {

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        if (getTargetFragment() == null) {
            getActivity().setResult(resultCode, intent);
            getActivity().finish();
        } else {
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        }

    }
}
