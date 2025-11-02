package com.example.RydeBookingService.controllers;


import com.example.RydeBookingService.dtos.CreateBookingResponseDto;
import com.example.RydeBookingService.services.BookingServiceImpl;
import com.example.RydeProject_EntityService.models.Booking;
import com.example.RydeProject_EntityService.models.BookingStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.RydeBookingService.dtos.CreateBookingDto;

@RestController
@RequestMapping("api/v1/booking")
public class BookingController {

    private BookingServiceImpl bookingService;

    public BookingController(BookingServiceImpl bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<CreateBookingResponseDto> createBooking(@RequestBody CreateBookingDto request){

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGNING_DRIVER)
                .startLocation(request.getStartLocation())
                .endLocation(request.getEndLocation())
                .build();


        return new ResponseEntity<>(bookingService.createBooking(request) , HttpStatus.CREATED);

    }
}
