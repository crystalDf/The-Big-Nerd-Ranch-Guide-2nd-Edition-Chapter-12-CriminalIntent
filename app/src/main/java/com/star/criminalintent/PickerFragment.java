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

import java.util.Calendar;
import java.util.Date;

public abstract class PickerFragment extends DialogFragment {

    protected static final String ARG_DATE = "date";

    public static final String EXTRA_DATE = "date";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT){

            final Calendar calendar = getCalendar();

            View view = getView(inflater, container);
            int pickerId = getPickerId();

            setDate(calendar, view, pickerId);

            setPickerButtonOnClickListener(calendar, view);

            return view;
        }

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar calendar = getCalendar();

        View view = getDialogView();
        int pickerId = getDialogPickerId();

        setDate(calendar, view, pickerId);

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle(getPickerTitle())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date date = getDate(calendar);
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }

    @NonNull
    protected Calendar getCalendar() {
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();

        if (date != null) {
            calendar.setTime(date);
        }

        return calendar;
    }

    protected abstract View getView(LayoutInflater inflater, ViewGroup container);

    protected abstract int getPickerId();

    protected abstract void setPickerButtonOnClickListener(final Calendar calendar, View view);

    protected abstract View getDialogView();

    protected abstract int getDialogPickerId();

    protected abstract int getPickerTitle();

    protected abstract void setDate(Calendar calendar, View view, int pickerId);

    protected abstract Date getDate(Calendar calendar);

    protected void sendResult(int resultCode, Date date) {
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
