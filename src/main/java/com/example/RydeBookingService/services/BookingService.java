package com.example.RydeBookingService.services;

import com.example.RydeBookingService.dtos.CreateBookingDto;
import com.example.RydeBookingService.dtos.CreateBookingResponseDto;
import com.example.RydeBookingService.dtos.UpdateBookingResponseDto;
import com.example.RydeBookingService.dtos.UpdateRequestBookingDto;
import com.example.RydeProject_EntityService.models.Booking;

public interface BookingService {

    public CreateBookingResponseDto createBooking(CreateBookingDto createBookingDto);

    UpdateBookingResponseDto updateBooking(UpdateRequestBookingDto requestDto , Long id);
}
