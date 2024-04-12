package com.bike.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bike.entities.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
