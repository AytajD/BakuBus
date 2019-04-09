package com.dadashova.aytaj.bakubustest.HttpClient;

import com.dadashova.aytaj.bakubustest.POJOS.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BusService {

    //GET request

    @GET("apiNew1")
    Call<ResponseModel> getBusData();



}
