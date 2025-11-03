package com.example.RydeBookingService.services;

import com.example.RydeBookingService.dtos.CreateBookingDto;
import com.example.RydeBookingService.dtos.CreateBookingResponseDto;
import com.example.RydeBookingService.dtos.DriverLocationDto;
import com.example.RydeBookingService.dtos.NearbyDriversRequestDto;
import com.example.RydeBookingService.repositories.BookingRepository;
import com.example.RydeBookingService.repositories.PassengerRepository;
import com.example.RydeProject_EntityService.models.Booking;
import com.example.RydeProject_EntityService.models.BookingStatus;
import com.example.RydeProject_EntityService.models.Passenger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class BookingServiceImpl implements BookingService{

    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;

    private final RestTemplate restTemplate;
    private static final String LOCATION_SERVICE ="https://localhost:9093";

    public BookingServiceImpl(PassengerRepository passengerRepository,
                              BookingRepository bookingRepository , RestTemplate restTemplate){
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails){

        Optional<Passenger> passenger = passengerRepository.findById(bookingDetails.getPassengerId());

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGNING_DRIVER)
                .startLocation(bookingDetails.getStartLocation())
                .endLocation(bookingDetails.getEndLocation())
                .passenger(passenger.get())
                .build();

        Booking newBooking = bookingRepository.save(booking);

        // API call to fetch nearly drivers
        NearbyDriversRequestDto requestDto = NearbyDriversRequestDto.builder()
                .latitude(bookingDetails.getStartLocation().getLatitude())
                .longitude(bookingDetails.getEndLocation().getLongitude())
                .build();

        ResponseEntity<DriverLocationDto[]> response = restTemplate.postForEntity(
                LOCATION_SERVICE + "/api/location/nearby/drivers", requestDto,
                DriverLocationDto[].class
        );
        if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null){

            List<DriverLocationDto> driverLocation = Arrays.asList(response.getBody());
            driverLocation.forEach(driverLocationDto -> {
                System.out.println(driverLocationDto.getDriverId() +"Latitude : " + driverLocationDto.getLatitude() + "Longitude : "
                        + driverLocationDto.getLongitude());
            });

        }



        return CreateBookingResponseDto.builder()
                        .bookingId(newBooking.getId())
                        .bookingStatus(newBooking.getBookingStatus().toString())
                        .driver(Optional.of(newBooking.getDriver()))
                        .build();

    }
}


