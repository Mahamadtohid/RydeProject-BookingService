package com.example.RydeBookingService.dtos;


import com.example.RydeProject_EntityService.models.ExactLocation;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingDto {

    private Long passengerId;

    private ExactLocation startLocation;

    private ExactLocation endLocation;
}
