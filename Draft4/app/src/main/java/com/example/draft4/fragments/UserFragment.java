package com.example.draft4.fragments;

/*
    This is the controller for users.

    For now, it handles creations of a new user
    and adding new locations.
 */

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.draft4.DataModifier;
import com.example.draft4.R;
import com.example.draft4.entities.Location;
import com.example.draft4.entities.User;

import java.util.UUID;

public class UserFragment extends Fragment {
    private EditText mUser, mStartLocation, mEndLocation;
    private DataModifier mDataModifier;
    private String mUserName, mLocationStart, mLocationEnd;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        mDataModifier = DataModifier.get(getActivity());
        mUserName = null;
        mLocationStart = null;
        mLocationEnd = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_main, parent, false);

        mUser = view.findViewById(R.id.fragment_user);
        mUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This is just here for showcase purposes.
                mUserName = s.toString();

                if(mUserName != null){
                    mDataModifier.addEntity(new User(UUID.randomUUID(), mUserName));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mStartLocation = view.findViewById(R.id.fragment_start_location);
        mStartLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This is just here for showcase purposes.
                mLocationStart = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mEndLocation = view.findViewById(R.id.fragment_end_location);
        mEndLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This is just here for showcase purposes.
                mLocationEnd = s.toString();

                if(mLocationStart != null & mLocationEnd != null){
                    mDataModifier.addEntity(new Location(UUID.randomUUID(), mLocationStart, mLocationEnd));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    // In order to write to a json file,
    // it needs to be done when the fragment
    // is put in pause (done by pressing the
    // back button, until the user is located
    // in the home window).
    @Override
    public void onPause(){
        super.onPause();

        mDataModifier.writeToJson();
        mDataModifier.readFromJson();
    }
}
