package com.example.project_hotel_booking.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_hotel_booking.R;
import com.example.project_hotel_booking.entity.Confirmation;
import com.example.project_hotel_booking.entity.Reservation;
import com.example.project_hotel_booking.entity.Room;
import com.example.project_hotel_booking.utils.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailFragment extends Fragment {
    private TextView reservationIdTextView;
    private TextView checkInDateTextView;
    private TextView checkOutDateTextView;
    private TextView roomTypeTextView;
    private TextView roomNumberTextView;
    private TextView confirmationIdTextView;
    private TextView amountTextView;
    private TextView paymentDateTextView;

private Button btn_feedback;

    public BookingDetailFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_booking_detail, container, false);

        // Initialize TextViews
        reservationIdTextView = rootView.findViewById(R.id.reservationIdTextView);
        checkInDateTextView = rootView.findViewById(R.id.checkInDateTextView);
        checkOutDateTextView = rootView.findViewById(R.id.checkOutDateTextView);
        roomTypeTextView = rootView.findViewById(R.id.roomTypeTextView);
        roomNumberTextView = rootView.findViewById(R.id.roomNumberTextView);
        confirmationIdTextView = rootView.findViewById(R.id.confirmationIdTextView);
        amountTextView = rootView.findViewById(R.id.amountTextView);
        paymentDateTextView = rootView.findViewById(R.id.paymentDateTextView);

        btn_feedback = rootView.findViewById(R.id.btnfeedback);
        Context context = getContext();

        // Check if the context is not null before proceeding
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("project", MODE_PRIVATE);
            int userId = sharedPreferences.getInt("user_id", -1);
            int selectedReservationId = sharedPreferences.getInt("selected_reservation_id", -1);

            RetrofitClient.getInstance().getApi().getUserReservationDetails(userId, selectedReservationId).enqueue(new Callback<JsonObject>()
            {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
                {
                    JsonArray jsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();

                    if (jsonArray == null || jsonArray.size() == 0)
                    {
                        reservationIdTextView.setText("json array is null");
                    }
                    else
                    {


                        for (JsonElement element : jsonArray)
                        {
                            reservationIdTextView.setText(String.valueOf(element.getAsJsonObject().get("reservation_id").getAsInt()));


                            checkInDateTextView.setText(element.getAsJsonObject().get("check_in_date").getAsString());
                            checkOutDateTextView.setText(element.getAsJsonObject().get("check_out_date").getAsString());

                            roomNumberTextView.setText(String.valueOf(element.getAsJsonObject().get("room_number").getAsInt()));
                            roomTypeTextView.setText(element.getAsJsonObject().get("room_type").getAsString());

                            confirmationIdTextView.setText(String.valueOf(element.getAsJsonObject().get("confirmation_id").getAsInt()));
                            amountTextView.setText(String.valueOf(element.getAsJsonObject().get("amount").getAsFloat()));
                            paymentDateTextView.setText(element.getAsJsonObject().get("payment_date").getAsString());
                        }


                    }
                }


                @Override
                public void onFailure(Call<JsonObject> call, Throwable t)
                {
                    Toast.makeText(context, "failure of retrofit", Toast.LENGTH_SHORT).show();
                }
            });
        }


        return rootView;

    }
}