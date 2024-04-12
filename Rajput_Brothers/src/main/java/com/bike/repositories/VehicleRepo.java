package com.bike.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bike.entities.Vehicle;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Integer> {

	public List<Vehicle> findVehicleByStatus(String status);
	
	@Query("SELECT f FROM Vehicle f WHERE " +"f.city LIKE CONCAT('%',:query, '%')"+"OR f.cityLocation LIKE CONCAT('%',:query, '%')"+"OR f.title LIKE CONCAT('%',:query, '%')")
	List<Vehicle> searchVehicles(String query);
}
