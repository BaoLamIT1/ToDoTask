package com.example.todotask.Fragment;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.todotask.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotificationFragment extends Fragment {
    private FloatingActionButton btnAdd;

    private FloatingActionButton btnReturn;

    private AutoCompleteTextView dropDownMenuWhen;
    ArrayAdapter<String> adapterItems;
    private LinearLayout VibreateLayout;
    private CheckBox checkBox_Vibrate;
    private CheckBox checkBox_Sound;
    private RelativeLayout soundPopup;
    private static final int REQUEST_SOUND_FILE = 101;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notification, container, false);

        return v;
    }

}