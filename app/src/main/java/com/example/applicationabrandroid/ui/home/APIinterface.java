package com.example.applicationabrandroid.ui.home;



import retrofit2.Call;
import retrofit2.http.GET;

public interface APIinterface {


    @GET("slider")
    Call<DelpapaResponse> getDelpapa();
}
