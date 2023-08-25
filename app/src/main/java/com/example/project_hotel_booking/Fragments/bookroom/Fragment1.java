package com.example.project_hotel_booking.Fragments.bookroom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_hotel_booking.R;

public class Fragment1 extends Fragment {



    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_1, container, false);

        // Replace the container with the SelectRoomFragment
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerFrameLayout, new SelectRoomFragment());
        fragmentTransaction.addToBackStack(null); // This allows you to go back to the DateActivityFragment if needed

        fragmentTransaction.commit();

        return rootView;
    }
}