package com.example.RydeBookingService.apis;

import com.example.RydeBookingService.dtos.DriverLocationDto;
import com.example.RydeBookingService.dtos.NearbyDriversRequestDto;
import com.example.RydeBookingService.dtos.RydeRequestDto;
import org.springframework.http.ResponseEntity;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RydeSocketApi {

    @POST("/api/socket/newride")
    Call<ResponseEntity<Boolean>> getNearbyDrivers(@Body RydeRequestDto requestDto);
}
