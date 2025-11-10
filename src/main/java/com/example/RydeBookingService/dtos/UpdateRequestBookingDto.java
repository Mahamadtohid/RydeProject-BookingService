package com.example.RydeBookingService.dtos;


import com.example.RydeProject_EntityService.models.BookingStatus;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRequestBookingDto {

    private String status;

    private Optional<Long> driverId;
}
