package com.bike.userserviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bike.dto.UserDto;
import com.bike.dto.VehicleDto;
import com.bike.entities.User;
import com.bike.entities.Vehicle;
import com.bike.repositories.UserRepo;
import com.bike.repositories.VehicleRepo;
import com.bike.services.UserService;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	private VehicleRepo vehicleRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto saveUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		User userSaved = this.userRepo.save(user);
		return this.modelMapper.map(userSaved, UserDto.class);
	}
	
	@Override
	public void removeMessage() {
		// TODO Auto-generated method stub
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
				.getSession();
		session.removeAttribute("message");
		
	}
	
	@Override
	public List<VehicleDto> searchVehicles(String query) {
		List<Vehicle> vehicles = this.vehicleRepo.searchVehicles(query);
		List<VehicleDto> vehicleDtos = vehicles.stream().map(v->this.modelMapper.map(v, VehicleDto.class)).collect(Collectors.toList());
		return vehicleDtos;
	}


}
