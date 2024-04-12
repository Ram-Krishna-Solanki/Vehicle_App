package com.bike.services;

import java.util.List;

import com.bike.dto.UserDto;
import com.bike.dto.VehicleDto;

public interface UserService {

	public UserDto saveUser(UserDto userDto);
	
	public void removeMessage();
	
	public List<VehicleDto> searchVehicles(String query);
}
