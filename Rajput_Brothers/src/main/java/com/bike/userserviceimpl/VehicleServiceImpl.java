package com.bike.userserviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bike.dto.VehicleDto;
import com.bike.entities.Vehicle;
import com.bike.exceptions.ResourceNotFoundException;
import com.bike.repositories.VehicleRepo;
import com.bike.services.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	private VehicleRepo vehicleRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public VehicleDto createVehicle(VehicleDto vehicleDto) {
		// TODO Auto-generated method stub
		Vehicle vehicle = this.modelMapper.map(vehicleDto,Vehicle.class);
		Vehicle vehicleSaved = this.vehicleRepo.save(vehicle);
		return this.modelMapper.map(vehicleSaved,VehicleDto.class);
	}

	@Override
	public VehicleDto updateVehicle(VehicleDto vehicleDto, Integer vehicleId) {
		// TODO Auto-generated method stub
		Vehicle vehicle = this.vehicleRepo.findById(vehicleId).orElseThrow(()-> new ResourceNotFoundException("vehicle","id",vehicleId));
		vehicle.setTitle(vehicleDto.getTitle());
		vehicle.setNumber(vehicleDto.getNumber());
		vehicle.setPrice(vehicleDto.getPrice());
		vehicle.setCityLocation(vehicleDto.getCityLocation());
		vehicle.setCity(vehicleDto.getCity());
		vehicle.setDescription(vehicleDto.getDescription());
		vehicle.setOwnerName(vehicleDto.getOwnerName());
		vehicle.setOwnerContact(vehicleDto.getOwnerContact());
	    vehicle.setInsuranceStatus(vehicleDto.getInsuranceStatus());
	    Vehicle vehicleSaved = vehicleRepo.save(vehicle);
	    return this.modelMapper.map(vehicleSaved,VehicleDto.class);
	 }

	@Override
	public void deleteVehicle(Integer vehicleId) {
		// TODO Auto-generated method stub
		Vehicle vehicle = this.vehicleRepo.findById(vehicleId).orElseThrow(()-> new ResourceNotFoundException("vehicle","id", vehicleId));
		this.vehicleRepo.delete(vehicle);
	}

	@Override
	public VehicleDto getVehicleById(Integer vehicleId) {
		// TODO Auto-generated method stub
		Vehicle vehicle = this.vehicleRepo.findById(vehicleId).orElseThrow(()-> new ResourceNotFoundException("vehicle","id", vehicleId));
		return this.modelMapper.map(vehicle,VehicleDto.class);
	}

	@Override
	public List<VehicleDto> getAllVehicle() {
		// TODO Auto-generated method stub
		List<Vehicle> vehicles = this.vehicleRepo.findAll();
		List<VehicleDto> vehicleDtos = vehicles.stream().map(vehicle->this.modelMapper.map(vehicles,VehicleDto.class)).collect(Collectors.toList());
		return vehicleDtos;
	}

}
