package com.tpe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tpe.CarDTO;
import com.tpe.domain.Car;
import com.tpe.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tpe.controller.request.CarRequest;
import com.tpe.service.CarService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/car")
@AllArgsConstructor
public class CarController {
	@Autowired
	private CarService carService;
	private final CarRepository carRepository;

	@PostMapping
	public ResponseEntity<Map<String, String>> saveCar(@RequestBody CarRequest carRequest) {

		System.out.println(carRequest.toString());
		carService.saveCar(carRequest);
		
		Map<String,String> map = new HashMap<>();
		
		map.put("message", "Car Successfully saved");
		map.put("success","true");
		
		return new ResponseEntity<>(map,HttpStatus.CREATED);
		
	}
	@GetMapping("/getall")
	public ResponseEntity<List<CarDTO>> getAllCars(){
		List<CarDTO> allCars = carService.getAllCars();
		return ResponseEntity.ok(allCars);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CarDTO> getById(@PathVariable Long id){
		CarDTO carDTO=carService.getById(id);
		return ResponseEntity.ok(carDTO);
	}


	@GetMapping
	String deneme() {
		return "deneme";
	}

}
