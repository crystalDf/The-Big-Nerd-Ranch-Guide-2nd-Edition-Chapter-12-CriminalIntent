package com.star.criminalintent;

import android.support.v4.app.Fragment;

import java.util.Date;

public class DatePickerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        Date date = (Date) getIntent().getSerializableExtra(CrimeFragment.EXTRA_DATE);

        return DatePickerFragment.newInstance(date);
    }
}
