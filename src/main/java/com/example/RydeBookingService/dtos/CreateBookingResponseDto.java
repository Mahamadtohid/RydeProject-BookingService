package com.example.RydeBookingService.dtos;


import com.example.RydeProject_EntityService.models.Driver;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingResponseDto {

    private Long bookingId;

    private String bookingStatus;

    private Optional<Driver> driver;


}
