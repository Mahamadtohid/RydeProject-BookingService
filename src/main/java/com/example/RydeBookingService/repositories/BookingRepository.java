package com.example.RydeBookingService.repositories;


import com.example.RydeProject_EntityService.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {


}
