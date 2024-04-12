package com.bike.dto;

import com.bike.entities.Category;

import lombok.Data;

@Data
public class VehicleDto {

	private int id;
	private String title;
	private String Number;
	private String price;
	private String km;
	private String cityLocation;
	private String city;
	private String description;
	private String ownerName;
	private String ownerContact;
	private String image;
	private String status;
	private String insuranceStatus;
	private Category category;
}
