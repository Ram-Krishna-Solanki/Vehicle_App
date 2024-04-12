package com.bike.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int km;
	private String title;
	private String Number;
	private String price;
	private String description;
	private String cityLocation;
	private String city;
	private String ownerName;
	private String ownerContact;
	private String image;
	private String status;
	private String insuranceStatus;
	@ManyToOne
	private Category category;
    
	
}
