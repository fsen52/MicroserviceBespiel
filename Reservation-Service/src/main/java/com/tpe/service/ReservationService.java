package com.tpe.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.tpe.CarDTO;
import com.tpe.controller.request.ReservationRequest;
import com.tpe.domain.Reservation;
import com.tpe.enums.ReservationStatus;
import com.tpe.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class ReservationService {


    private EurekaClient client;

    private RestTemplate restTemplate;


    public void saveReservation(Long carId, ReservationRequest request) throws ResourceNotFoundException {
        InstanceInfo instanceInfo = client.getApplication("car-service").getInstances().get(0);
        String baseUrl = instanceInfo.getHomePageUrl();

        String path="/car/";
        String servicePath = baseUrl+path+carId.toString();

        ResponseEntity<CarDTO> carDTOResponse= restTemplate.getForEntity(servicePath, CarDTO.class);

        if(!(carDTOResponse.getStatusCode()== HttpStatus.OK)){
            throw new ResourceNotFoundException("Car not found with id" + carId);
        }

        Reservation reservation = new Reservation();

        reservation.setCarId(carDTOResponse.getBody().getId());
        reservation.setPickUpTime(request.getPickUpTime());
        reservation.setDropOffTime(request.getDropOffTime());
        reservation.setPickUpLocation(request.getPickUpLocation());
        reservation.setDropOffLocation(request.getDropOffLocation());

        reservation.setStatus(ReservationStatus.CREATED);



    }

}
