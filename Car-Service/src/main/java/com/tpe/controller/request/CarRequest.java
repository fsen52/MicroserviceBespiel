package com.tpe.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarRequest {

	private String brand;
	
	private String model;
	
	private Integer doors;
	
	private Double pricePerHour;
	
	private Integer years;

	@Override
	public String toString() {
		return "CarRequest{" +
				"brand='" + brand + '\'' +
				", model='" + model + '\'' +
				", doors=" + doors +
				", pricePerHour=" + pricePerHour +
				", years=" + years +
				'}';
	}
}
