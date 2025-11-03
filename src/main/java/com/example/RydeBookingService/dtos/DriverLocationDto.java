package com.example.RydeBookingService.dtos;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverLocationDto {

    String driverId;

    Double latitude;

    Double longitude;
}
