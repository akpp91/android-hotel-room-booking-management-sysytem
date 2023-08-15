package com.example.project_hotel_booking.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_hotel_booking.R;
import com.example.project_hotel_booking.utils.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment3 extends Fragment {

    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private Button btnChangePassword;

    public Fragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        etOldPassword = view.findViewById(R.id.etOldPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleChangePasswordClick();
            }
            });

        return view;
    }

    private void handleChangePasswordClick() {
        String oldPassword = etOldPassword.getText().toString();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("project", Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("user_id", -1);

        Log.d("User ID", "User ID retrieved from SharedPreferences: " + user_id);

        RetrofitClient.getInstance().getApi().RetriveUser(user_id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null && response.body().has("data")) {
                    JsonObject jsonObject = response.body().getAsJsonObject("data");

                    Log.d("User ID", "User obj retrieved from DB: " + jsonObject);

                    if (jsonObject != null && jsonObject.has("password")) {
                        String storedPassword = jsonObject.get("password").getAsString();

                        if (oldPassword.equals(storedPassword)) {
                            String newPassword = etNewPassword.getText().toString();
                            String confirmPassword = etConfirmPassword.getText().toString();

                            // Validate passwords and perform change password logic here
                            if (newPassword.equals(confirmPassword)) {
                                Toast.makeText(getContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
//                                RetrofitClient.getInstance().getApi()
                            } else {
                                Toast.makeText(getContext(), "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Incorrect old password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Password data not found in response", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Failed to get user data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
