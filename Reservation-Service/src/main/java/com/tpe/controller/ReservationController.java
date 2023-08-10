package com.tpe.controller;


import com.netflix.discovery.EurekaClient;
import com.tpe.ReservationDTO;
import com.tpe.controller.request.ReservationRequest;
import com.tpe.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController {

    private ReservationService reservationService;


    @PostMapping("/{carId}")
    public ResponseEntity<Map<String,String>> saveReservation(@PathVariable Long carId,
                                                              @RequestBody ReservationRequest request){

        reservationService.saveReservation(carId, request);

        Map<String,String> map = new HashMap<>();
        map.put("message", "Reservation Successfuly Created");
        map.put("success", "true");

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations(){
       List<ReservationDTO> allReservations  = reservationService.getAllReservations();

       return ResponseEntity.ok(allReservations);
    }

}
