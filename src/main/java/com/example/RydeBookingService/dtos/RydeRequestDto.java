package com.example.RydeBookingService.dtos;




//import com.example.RydeWebsocketServer.controller.ExactLocation;
import com.example.RydeProject_EntityService.models.ExactLocation;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RydeRequestDto {

    private Long passengerId;

    private ExactLocation startLocation;

    private ExactLocation endLocation;

    private List<Long> driverId;
}

