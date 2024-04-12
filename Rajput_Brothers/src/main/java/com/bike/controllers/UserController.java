package com.bike.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bike.entities.Cart;
import com.bike.entities.User;
import com.bike.entities.Vehicle;
import com.bike.repositories.CategoryRepo;
import com.bike.repositories.UserRepo;
import com.bike.repositories.VehicleRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private VehicleRepo vehicleRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
//	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d-MMM-yyyy");  
//	   static LocalDateTime now = LocalDateTime.now();  
//	
	public static String mypickupDate;
	public static String mypickupTime;
	public static String mydropoffDate;
	public static String mydropoffTime;
	public static Long mydateDiff;
	
	
	
	@ModelAttribute
	public void commonData(Principal principal,Model model)
	{
		String email = principal.getName();
		User user = this.userRepo.findByEmail(email);
		model.addAttribute("user", user);
	}
	
	@GetMapping("/user-dashboard")
	public String userDashboard(Principal principal ,Model model)
	{
		String email = principal.getName();
		User user = this.userRepo.findByEmail(email);
		model.addAttribute("user", user);
		return "user/user-dashboard";
	}
	
	@GetMapping("/view-category")
	public String viewCategory(Model model)
	{
		model.addAttribute("categories",this.categoryRepo.findAll());
		return "user/view-category";
	}
	
	
	
	@GetMapping("/view-vehicle")
	public String viewVehicle(@RequestParam("pickup-date")String pickupDate,@RequestParam("pickup-time")String pickupTime,@RequestParam("dropoff-date")String dropoffDate,@RequestParam("dropoff-time")String dropoffTime,Model model,HttpSession session)
	{
		if(pickupDate.isEmpty())
		{
			session.setAttribute("message","Please select pickup date");
			return "redirect:/";
		}
		if(pickupTime.isEmpty())
		{
			model.addAttribute("pickupdate",pickupDate);
			session.setAttribute("message","Please select pickup time");
			return "redirect:/";
		}
		if(dropoffDate.isEmpty())
		{
			session.setAttribute("message","Please select dropoff date");
			return "redirect:/";
		}
		if(dropoffTime.isEmpty())
		{
			session.setAttribute("message","Please select dropoff time");
			return "redirect:/";
		}
		List<Vehicle> vehicleByStatus = this.vehicleRepo.findVehicleByStatus("available");
		mypickupDate=pickupDate;
		mypickupTime=pickupTime;
		mydropoffDate=dropoffDate;
		mydropoffTime=dropoffTime;

		
		
		//for strdate = 2017 July 25

		  DateTimeFormatter df = DateTimeFormatter.ofPattern("d-MMM-yyyy");
		  LocalDate  d1 = LocalDate.parse(pickupDate, df);
		  LocalDate  d2 = LocalDate.parse(dropoffDate, df);

		  Long dateDiff = ChronoUnit.DAYS.between(d1,d2);
		  mydateDiff=dateDiff;
		 
		  //must be positive not negative date difference
		  if(dateDiff<0)
		  {
			  session.setAttribute("message","pickupDate must be smaller then dropoffDate");
			  return "redirect:/";
		  }
		  
//		  if(pickupDate.compareTo(dropoffDate)>0)
//		  {
//			  session.setAttribute("message","pickupDate must be smaller then dropoffDate");
//			  return "redirect:/"; 
//		  }
		  
		  
	    System.out.println("Diff b/w 2 dates: "+dateDiff);
		
		model.addAttribute("mypickupDate",pickupDate);
		model.addAttribute("mypickupTime",pickupTime);
		model.addAttribute("mydropoffDate",dropoffDate);
		model.addAttribute("mydropoffTime",dropoffTime);
		model.addAttribute("vehicles",vehicleByStatus);
		model.addAttribute("mydateDiff", mydateDiff);
		return "user/view-vehicle";
	}
	
	
	@GetMapping("/search")
	public String getVehicleBySearch(@RequestParam("query")String query, Model model) {
		List<Vehicle> vehicles = this.vehicleRepo.searchVehicles(query);
		model.addAttribute("vehicles", vehicles);
		model.addAttribute("mypickupDate",mypickupDate);
		model.addAttribute("mypickupTime",mypickupTime);
		model.addAttribute("mydropoffDate",mydropoffDate);
		model.addAttribute("mydropoffTime",mydropoffTime);
		model.addAttribute("mydateDiff", mydateDiff);
		return "user/view-vehicle";
	}
	
	
	@GetMapping("/view-all-vehicle")
	public String allVehicle(Model model)
	{
		List<Vehicle> vehicles = this.vehicleRepo.findAll();
		model.addAttribute("vehicles", vehicles);
		model.addAttribute("mypickupDate",mypickupDate);
		model.addAttribute("mypickupTime",mypickupTime);
		model.addAttribute("mydropoffDate",mydropoffDate);
		model.addAttribute("mydropoffTime",mydropoffTime);
		model.addAttribute("mydateDiff", mydateDiff);
		return "user/all-vehicle";
	}
	
	@GetMapping("/available-vehicle")
	public String availableVehicle(Model model)
	{
		List<Vehicle> vehicles = this.vehicleRepo.findVehicleByStatus("Available");
		model.addAttribute("vehicles", vehicles);
		model.addAttribute("mypickupDate",mypickupDate);
		model.addAttribute("mypickupTime",mypickupTime);
		model.addAttribute("mydropoffDate",mydropoffDate);
		model.addAttribute("mydropoffTime",mydropoffTime);
		model.addAttribute("mydateDiff", mydateDiff);
		return "user/available-vehicle";
	}
	
	
	@GetMapping("/booked")
	public String addCart(@RequestParam("vehicleId")Integer vehicleId,Model model)
	{
		Vehicle vehicle = this.vehicleRepo.findById(vehicleId).get();
		model.addAttribute("vehicle",vehicle);
		
		
		model.addAttribute("mypickupDate",mypickupDate);
		model.addAttribute("mypickupTime",mypickupTime);
		model.addAttribute("mydropoffDate",mydropoffDate);
		model.addAttribute("mydropoffTime",mydropoffTime);
		model.addAttribute("mydateDiff", mydateDiff);
		Cart.cart.add(vehicle);
		return "user/booked";
	}
	
	

	
	
}
