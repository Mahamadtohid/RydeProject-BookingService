package com.example.RydeBookingService.controllers;


import com.example.RydeBookingService.dtos.*;
import com.example.RydeBookingService.repositories.PassengerRepository;
import com.example.RydeBookingService.services.BookingServiceImpl;
import com.example.RydeProject_EntityService.models.Booking;
import com.example.RydeProject_EntityService.models.BookingStatus;
import com.example.RydeProject_EntityService.models.Passenger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/booking")
public class BookingController {

    private BookingServiceImpl bookingService;

    public BookingController(BookingServiceImpl bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<CreateBookingResponseDto> createBooking(@RequestBody CreateBookingDto request){


        return new ResponseEntity<>(bookingService.createBooking(request) , HttpStatus.CREATED);

    }


    @PatchMapping("/{bookigId}")
    public ResponseEntity<UpdateBookingResponseDto> updateBooking(@RequestBody UpdateRequestBookingDto requestDto , @PathVariable Long id){

        return new ResponseEntity<>(bookingService.updateBooking(requestDto , id) , HttpStatus.OK);

    }
}
