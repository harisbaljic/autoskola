package com.autoskola.instruktori.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.gps.GpsTask;

/**
 * Created by haris on 2/13/15.
 */
public class FragmentSettings extends Fragment {

    private EditText mTxtInterval;
    private EditText mTxtDistance;
    private Button btnSaveSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mTxtDistance = (EditText) view.findViewById(R.id.txtMinDistance);
        mTxtInterval = (EditText) view.findViewById(R.id.txtMinTime);
        btnSaveSettings = (Button) view.findViewById(R.id.btnSaveSettings);
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtDistance = mTxtDistance.getText().toString().trim();
                String txtInterval = mTxtInterval.getText().toString().trim();

                if (txtDistance.length() > 0) {
                    GpsTask.getInstance().setMinimumDistanceBetweenLocationUpdate(txtDistance, getActivity());
                }
                if (txtInterval.length() > 0) {
                    GpsTask.getInstance().setMinimumIntervalBetweenLocationUpdate(txtInterval, getActivity());
                }

                hideKeyboard();

                // Apply settings
                GpsTask.getInstance().applyGpsLocationUpdateSettings(getActivity());
            }
        });
        return view;
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mTxtDistance.setText(GpsTask.getInstance().getMinimumDistanceBetweenLocationUpdate(getActivity()));
        mTxtInterval.setText(GpsTask.getInstance().getMinimumIntervalBetweenLocationUpdate(getActivity()));
    }
}
