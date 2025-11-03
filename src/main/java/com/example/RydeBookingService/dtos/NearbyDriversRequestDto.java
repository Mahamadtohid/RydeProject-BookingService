package com.example.RydeBookingService.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NearbyDriversRequestDto {

    Double latitude;

    Double longitude;
}
