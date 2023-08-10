package com.tpe.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.tpe.CarDTO;
import com.tpe.ReservationDTO;
import com.tpe.controller.request.ReservationRequest;
import com.tpe.domain.Reservation;
import com.tpe.enums.ReservationStatus;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationService {


    private EurekaClient client;

    private RestTemplate restTemplate;
    @Autowired
    private ReservationRepository reservationRepository;

    private ModelMapper modelMapper;


    public void saveReservation(Long carId, ReservationRequest request) throws ResourceNotFoundException {
        InstanceInfo instanceInfo = client.getApplication("car-service").getInstances().get(0);
        String baseUrl = instanceInfo.getHomePageUrl();

        String path="/car/";
        String servicePath = baseUrl+path+carId.toString();

        ResponseEntity<CarDTO> carDTOResponse= restTemplate.getForEntity(servicePath, CarDTO.class);

        if(!(carDTOResponse.getStatusCode()== HttpStatus.OK)){
            throw new ResourceNotFoundException("Car not found with id" + carId);
        }

        CarDTO carDTO=carDTOResponse.getBody();

        Reservation reservation = new Reservation();

        reservation.setCarId(carDTO.getId());
        reservation.setPickUpTime(request.getPickUpTime());
        reservation.setDropOffTime(request.getDropOffTime());
        reservation.setPickUpLocation(request.getPickUpLocation());
        reservation.setDropOffLocation(request.getDropOffLocation());

        reservation.setStatus(ReservationStatus.CREATED);

        double tp = totalPrice(request.getPickUpTime(),request.getDropOffTime(),carDTO);
        reservation.setTotalPrice(tp);

        reservationRepository.save(reservation);

    }

    private Double totalPrice(LocalDateTime pickUpTime, LocalDateTime dropOffTime, CarDTO carDTO){
        Long hours = (new Reservation()).getTotalHours(pickUpTime,dropOffTime);
        return carDTO.getPricePerHour()*hours;
    }

    public List<ReservationDTO> getAllReservations() {
        List<Reservation> rList = reservationRepository.findAll();
        List<ReservationDTO> rDTOList= rList.stream().map(this::mapReservationToReservationDTO).collect(Collectors.toList());
        return rDTOList ;
    }


    private ReservationDTO mapReservationToReservationDTO(Reservation reservation){
        ReservationDTO reservationDTO = modelMapper.map(reservation, ReservationDTO.class);
        return reservationDTO;
    }



}
