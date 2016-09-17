package com.star.criminalintent;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public abstract class PickerFragment extends DialogFragment {

    protected static final String ARG_DATE = "date";
    protected Calendar mCalendar;

    public static final String EXTRA_DATE = "com.star.date";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        mCalendar = Calendar.getInstance();
        mCalendar.setTime(date);
    }

    protected abstract void setDate();

    protected abstract Date getDate();

    protected void sendResult(int resultCode, Date date) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        if (getTargetFragment() == null) {
            getActivity().setResult(resultCode, intent);
            getActivity().finish();
        } else {
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
            dismiss();
        }
    }

}
