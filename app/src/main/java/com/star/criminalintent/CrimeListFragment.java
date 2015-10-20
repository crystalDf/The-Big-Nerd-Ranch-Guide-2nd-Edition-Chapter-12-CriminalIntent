package com.star.criminalintent;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;


public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mCrimeAdapter;

    private int mCrimePosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mCrimeRecyclerView.setLayoutManager(linearLayoutManager);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.getInstance(getContext());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mCrimeAdapter == null) {
            mCrimeAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mCrimeAdapter);
        } else {
            mCrimeAdapter.notifyItemChanged(mCrimePosition);
        }

    }

    private class CrimeHolder extends RecyclerView.ViewHolder {

        private Crime mCrime;

        private TextView mTitleTextView;
        private CheckBox mSolvedCheckBox;
        private TextView mDateTextView;

        public CrimeHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCrimePosition = getAdapterPosition();
                    Intent intent = CrimePagerActivity.newIntent(getContext(), mCrime.getId());
                    startActivity(intent);
                }
            });

            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_title_text_view);
            mSolvedCheckBox = (CheckBox)
                    itemView.findViewById(R.id.list_item_crime_solved_check_box);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_date_text_view);
        }

        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
            mDateTextView.setText(mCrime.getFormattedDate());
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());

            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);

            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
