package com.example.RydeBookingService.apis;

import com.example.RydeBookingService.dtos.DriverLocationDto;
import com.example.RydeBookingService.dtos.NearbyDriversRequestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LocationServiceApi {

    @POST("/api/location/nearby/drivers")
    Call<DriverLocationDto[]> getNearbyDrivers(@Body NearbyDriversRequestDto requestDto);
}
