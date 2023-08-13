package com.example.project_hotel_booking.utils;

import com.example.project_hotel_booking.entity.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {
    String BASE_URL ="http://192.168.0.110:4004";

    @POST("/user/login")
    Call<JsonObject> loginUser(@Body User user);

    @POST("/user/register")
    Call<JsonObject> registerUser(@Body User user);

    @GET("/room/getAllRooms")
    Call<JsonObject> getAllRooms();

    @GET("/mobile/")
    Call<JsonObject> getAllMobiles();

    @POST("/reservation/book_Reservation")
    Call<JsonObject> bookReservation(@Body JsonObject reservation);

    @GET("/reservation/user_reservations/{userId}")
    Call<JsonObject> getUserReservations(@Path("userId") int userId);



}
