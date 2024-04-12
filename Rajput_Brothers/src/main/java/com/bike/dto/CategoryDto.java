package com.bike.dto;

import java.util.ArrayList;
import java.util.List;

import com.bike.entities.Vehicle;

import lombok.Data;

@Data
public class CategoryDto {

	private int id;
	private String title;
	private String description;
	private String image;
	private List<Vehicle> vehicle=new ArrayList<>();
}
