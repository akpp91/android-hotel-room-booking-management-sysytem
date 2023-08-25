package com.example.project_hotel_booking.Fragments.bookroom;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_hotel_booking.R;
import com.example.project_hotel_booking.activity.MainActivity;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateActivityFragment extends Fragment {

    private TextView textCheckInDate;
    private TextView textCheckOutDate;
    private Button buttonConfirm;
    private Calendar calendar;
    private SharedPreferences sharedPreferences;

    public DateActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_activity, container, false);

        textCheckInDate = view.findViewById(R.id.text_checkin_date);
        textCheckOutDate = view.findViewById(R.id.text_checkout_date);
        buttonConfirm = view.findViewById(R.id.button_confirm);
        calendar = Calendar.getInstance();

        textCheckInDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(true);
            }
        });

        textCheckOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(false);
            }
        });

        // Handle the confirm button click if needed
        // Inside the onClick method of buttonConfirm in DateActivityFragment
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your code for handling the confirm button click
                sharedPreferences = requireActivity().getSharedPreferences("project", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("check_in_date", textCheckInDate.getText().toString());
                editor.putString("check_out_date", textCheckOutDate.getText().toString());
                editor.apply();



                // Replace the current fragment with the ConfirmationFragment
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ConfirmationFragment confirmationFragment = new ConfirmationFragment();
                fragmentTransaction.replace(R.id.containerFrameLayout, confirmationFragment); // R.id.fragment_container should be the ID of the container in your activity's layout
                fragmentTransaction.addToBackStack(null); // This allows you to go back to the DateActivityFragment if needed
                fragmentTransaction.commit();
            }
        });
        // Handle the back button press using the fragment's view
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    // Create an instance of the SelectRoomFragment
                    SelectRoomFragment selectRoomFragment = new SelectRoomFragment();

                    // Start a new fragment transaction
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                    // Replace the current fragment with SelectRoomFragment
                    transaction.replace(R.id.containerFrameLayout, selectRoomFragment);

                    // Remove the current fragment from the back stack
//                    getParentFragmentManager().popBackStack();

                    // Commit the transaction
                    transaction.commit();
                    // Request MainActivity to navigate back to appropriate fragment
                    if (getActivity() instanceof MainActivity) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.navigateToSelectRoomFragment();
                    }
                    return true;
                }
                return false;
            }


        });

        return view;
    }

    private void showDatePickerDialog(final boolean isCheckInDate) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        if (isCheckInDate) {
                            updateCheckInDate();
                        } else {
                            updateCheckOutDate();
                        }
                    }
                },
                year,
                month,
                day
        );

        // Set a minimum date if needed
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }

    private void updateCheckInDate() {
        Date checkInDate = calendar.getTime();
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        try {
            String formattedDate = outputFormat.format(inputFormat.parse(inputFormat.format(checkInDate)));
            textCheckInDate.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void updateCheckOutDate() {
        Date checkOutDate = calendar.getTime();
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        try {
            String formattedDate = outputFormat.format(inputFormat.parse(inputFormat.format(checkOutDate)));
            textCheckOutDate.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
