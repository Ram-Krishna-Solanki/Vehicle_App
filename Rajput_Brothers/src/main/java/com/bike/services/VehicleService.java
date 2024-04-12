package com.bike.services;

import java.util.List;

import com.bike.dto.VehicleDto;

public interface VehicleService {

	public abstract VehicleDto createVehicle(VehicleDto vehicleDto);
	
	public abstract VehicleDto updateVehicle(VehicleDto vehicleDto,Integer vehicleId);
	
	public abstract void deleteVehicle(Integer vehicleId);
	
	public abstract VehicleDto getVehicleById(Integer vehicleId);
	
	public abstract List<VehicleDto> getAllVehicle();
}
