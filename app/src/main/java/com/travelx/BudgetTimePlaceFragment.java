package com.travelx;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.awt.font.TextAttribute;


public class BudgetTimePlaceFragment extends Fragment {

    TextView budget,duration;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.budget_time_place_fragment, container, false);

        budget = (TextView) rootView.findViewById(R.id.budget);
        duration = (TextView) rootView.findViewById(R.id.time);
        System.out.println(Common.place_selected.getBudget());
        System.out.println(Common.place_selected.getDuration());
        budget.setText(Integer.toString(Common.place_selected.getBudget()));
        duration.setText(Common.place_selected.getDuration());
        return rootView;
    }
}