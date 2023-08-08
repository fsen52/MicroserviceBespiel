package com.tpe.service;

import com.tpe.CarDTO;
import com.tpe.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpe.controller.request.CarRequest;
import com.tpe.domain.Car;
import com.tpe.repository.CarRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarService {


    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ModelMapper modelMapper;


    public void saveCar(CarRequest carRequest) {
        Car car = modelMapper.map(carRequest, Car.class);
        carRepository.save(car);
    }

    public List<CarDTO> getAllCars() {
        List<Car> carList = carRepository.findAll();
        List<CarDTO> carDTOList = carList.stream().map(this::mapCarToCarDTO).collect(Collectors.toList());
        return carDTOList;
    }

    public CarDTO getById(Long id){
        Car car = carRepository.findById(id).orElseThrow();
        CarDTO carDTO=mapCarToCarDTO(car);
        return carDTO;

    }
    private CarDTO mapCarToCarDTO(Car car) {
        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        return carDTO;
    }










}
