package com.example.RydeBookingService.services;

import com.example.RydeBookingService.apis.LocationServiceApi;
import com.example.RydeBookingService.apis.RydeSocketApi;
import com.example.RydeBookingService.dtos.*;
import com.example.RydeBookingService.repositories.BookingRepository;
import com.example.RydeBookingService.repositories.DriverRepository;
import com.example.RydeBookingService.repositories.PassengerRepository;
import com.example.RydeProject_EntityService.models.Booking;
import com.example.RydeProject_EntityService.models.BookingStatus;
import com.example.RydeProject_EntityService.models.Driver;
import com.example.RydeProject_EntityService.models.Passenger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class BookingServiceImpl implements BookingService{

    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;

    private final RestTemplate restTemplate;

    private final RydeSocketApi rydeSocketApi;


    private final LocationServiceApi locationServiceApi;
    private final DriverRepository driverRepository;
//    private static final String LOCATION_SERVICE ="https://localhost:9093";

    public BookingServiceImpl(PassengerRepository passengerRepository,
                              BookingRepository bookingRepository , RestTemplate restTemplate,
                              LocationServiceApi locationServiceApi,
                              RydeSocketApi rydeSocketApi,
                              DriverRepository driverRepository){
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = restTemplate;
        this.locationServiceApi = locationServiceApi;
        this.driverRepository = driverRepository;
        this.rydeSocketApi = rydeSocketApi;
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

        processNearbyDriversAsync(requestDto , bookingDetails.getPassengerId());
//
//        ResponseEntity<DriverLocationDto[]> response = restTemplate.postForEntity(
//                LOCATION_SERVICE + "/api/location/nearby/drivers", requestDto,
//                DriverLocationDto[].class
//        );
//        if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null){
//
//            List<DriverLocationDto> driverLocation = Arrays.asList(response.getBody());
//            driverLocation.forEach(driverLocationDto -> {
//                System.out.println(driverLocationDto.getDriverId() +"Latitude : " + driverLocationDto.getLatitude() + "Longitude : "
//                        + driverLocationDto.getLongitude());
//            });
//
//        }




        return CreateBookingResponseDto.builder()
                        .bookingId(newBooking.getId())
                        .bookingStatus(newBooking.getBookingStatus().toString())
                        .driver(Optional.of(newBooking.getDriver()))
                        .build();

    }


    private void processNearbyDriversAsync(NearbyDriversRequestDto requestDto , Long passengerId){
        Call<DriverLocationDto[]> call = locationServiceApi.getNearbyDrivers(requestDto);

        call.enqueue(new Callback<DriverLocationDto[]>() {
            @Override
            public void onResponse(Call<DriverLocationDto[]> call, Response<DriverLocationDto[]> response) {

                        if(response.isSuccessful() && response.body() != null){

            List<DriverLocationDto> driverLocation = Arrays.asList(response.body());
            driverLocation.forEach(driverLocationDto -> {
                System.out.println(driverLocationDto.getDriverId() +"Latitude : " + driverLocationDto.getLatitude() + "Longitude : "
                        + driverLocationDto.getLongitude());

                raiseRideRequestAsync(RydeRequestDto.builder().passengerId(passengerId).build());
            });

        }else{
                            System.out.println("Request Failed : " + response.message());
                        }

            }

            @Override
            public void onFailure(Call<DriverLocationDto[]> call, Throwable t) {

                t.printStackTrace();

            }
        });
    }


    @Override
    public UpdateBookingResponseDto updateBooking(UpdateRequestBookingDto requestDto , Long id) {

        Optional<Driver> driver = driverRepository.findById(requestDto.getDriverId().get());
//        if(driver.isPresent()){

            bookingRepository.updateBookingStatusAndDriverById(id , BookingStatus.SHEDULED, driver.get());

            Optional<Booking> booking = bookingRepository.findById(id);

            return new UpdateBookingResponseDto().builder()
                    .bookingId(id)
                    .status(booking.get().getBookingStatus())
                    .driver(Optional.ofNullable(booking.get().getDriver()))
                    .build();


//        return null;

    }




    private void raiseRideRequestAsync(RydeRequestDto requestDto){
        Call<ResponseEntity<Boolean>> call = rydeSocketApi.getNearbyDrivers(requestDto);
        call.enqueue(new Callback<ResponseEntity<Boolean>>() {
            @Override
            public void onResponse(Call<ResponseEntity<Boolean>> call, Response<ResponseEntity<Boolean>> response) {


                if(response.isSuccessful() && response.body() != null){

                    Boolean result = response.body().getBody();
                    System.out.println("Driver Response is " + result);




                }else{
                    System.out.println("Request Failed : " + response.message());
                }


            }

            @Override
            public void onFailure(Call<ResponseEntity<Boolean>> call, Throwable throwable) {

                throwable.printStackTrace();

            }
        });
    }

}


