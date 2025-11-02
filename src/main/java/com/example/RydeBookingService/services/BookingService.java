package com.example.RydeBookingService.services;

import com.example.RydeBookingService.dtos.CreateBookingDto;
import com.example.RydeBookingService.dtos.CreateBookingResponseDto;
import com.example.RydeProject_EntityService.models.Booking;

public interface BookingService {

    public CreateBookingResponseDto createBooking(CreateBookingDto createBookingDto);
}
