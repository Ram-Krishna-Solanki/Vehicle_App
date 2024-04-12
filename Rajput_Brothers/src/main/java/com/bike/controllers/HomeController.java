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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bike.dto.UserDto;
import com.bike.entities.User;
import com.bike.entities.Vehicle;
import com.bike.exceptions.AgreeementNotAcceptException;
import com.bike.helper.Message;
import com.bike.repositories.CategoryRepo;
import com.bike.repositories.UserRepo;
import com.bike.repositories.VehicleRepo;
import com.bike.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private VehicleRepo vehicleRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static String mypickupDate;
	public static String mypickupTime;
	public static String mydropoffDate;
	public static String mydropoffTime;
	public static Long mydateDiff;
	

	@RequestMapping(path="/",method=RequestMethod.GET)
	public String home()
	{
		return "home";
	}
	
	@RequestMapping(path="/register",method=RequestMethod.GET)
	public String register(Model model)
	{
		model.addAttribute("userDto",new UserDto());
		return "register";
	}
	
	@PostMapping("/user-register")
	public String userRegister(@Valid @ModelAttribute("userDto")UserDto userDto,BindingResult result,@RequestParam(value="agreement",defaultValue = "false")boolean agreement,Model model,HttpSession session)
	{
		System.out.println(userDto);
		System.out.println(agreement);
		
		try {
			    if(!agreement) {
			    System.out.println(agreement);	
				throw new AgreeementNotAcceptException("Please agreed terms & condition");
			    }
			    
			    if (result.hasErrors()) {
					model.addAttribute("userDto", userDto);
					System.out.println(result.getFieldErrors("name"));
					return "register";
				}
			    //save to DB
			    userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
			    userDto.setRole("ROLE_USER");
			    this.userService.saveUser(userDto);
			    model.addAttribute("userDto",new UserDto());
			    session.setAttribute("message", new Message("Register Successfully!!","alert-success"));
			    return "redirect:/register";
		}
		catch(AgreeementNotAcceptException ex)
		{
		   System.out.println(ex.getMessage());
		   session.setAttribute("message", new Message("Please agreed terms & conditions!!","alert-danger"));
		}
		return "register";
	}
	
	@GetMapping("/login")
	public String login()
	{
		return "login";
	}
	
	
	@GetMapping("/view-category")
	public String viewCategory(Model model)
	{
		model.addAttribute("categories",this.categoryRepo.findAll());
		return "view-category";
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
		return "view-vehicle";
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
		return "view-vehicle";
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
		return "all-vehicle";
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
		return "available-vehicle";
	}
	

	
	
	

}
