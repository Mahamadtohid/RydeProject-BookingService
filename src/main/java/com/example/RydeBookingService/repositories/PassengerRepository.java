package com.example.RydeBookingService.repositories;

import com.example.RydeProject_EntityService.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger , Long> {
}
