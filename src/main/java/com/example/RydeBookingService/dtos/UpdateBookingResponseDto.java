package com.example.RydeBookingService.dtos;


import com.example.RydeProject_EntityService.models.BookingStatus;
import com.example.RydeProject_EntityService.models.Driver;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBookingResponseDto {

    private Long bookingId;

//    Enumerated(EnumType.STRING)
    private BookingStatus status;

    private Optional<Driver> driver;
}
