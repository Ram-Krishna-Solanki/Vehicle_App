package com.bike.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bike.entities.User;
import com.bike.entities.Vehicle;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	public abstract User findByEmail(String email);
	
}
