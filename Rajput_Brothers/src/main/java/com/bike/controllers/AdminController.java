package com.bike.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bike.dto.CategoryDto;
import com.bike.dto.VehicleDto;
import com.bike.entities.Category;
import com.bike.entities.User;
import com.bike.entities.Vehicle;
import com.bike.repositories.CategoryRepo;
import com.bike.repositories.UserRepo;
import com.bike.repositories.VehicleRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private VehicleRepo vehicleRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/admin-dashboard")
	public String adminDashboard(Principal principal,Model model)
	{
		List<Category> categories = this.categoryRepo.findAll();
		model.addAttribute("categories", categories);
		List<Vehicle> vehicles = this.vehicleRepo.findAll();
		model.addAttribute("vehicles", vehicles);
		List<User> users = this.userRepo.findAll();
		model.addAttribute("users", users);
		return "admin/admin-dashboard";
	}
	
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String email = principal.getName();
		User user = this.userRepo.findByEmail(email);
		model.addAttribute("user", user);
	} 
	
	
	@PostMapping("/add-category")
	public String addCategory(@ModelAttribute("categoryDto")CategoryDto categoryDto,HttpSession session,@RequestParam("categoryImage")MultipartFile file) throws IOException
	{
		//uploading file
				if(file.isEmpty())
				{
					//empty file
					categoryDto.setImage("default-car.png");
				}
				else {
				   //upload file in a folder and save in contact
					categoryDto.setImage(file.getOriginalFilename());
					File folderLocation = new ClassPathResource("static/images").getFile();
					//input stream,path where write data,copy
					Path path = Paths.get(folderLocation.getAbsolutePath()+File.separator+file.getOriginalFilename());
					Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
					System.out.println("file uploaded successfully!!");
				}
				
		Category category = this.modelMapper.map(categoryDto, Category.class);
		this.categoryRepo.save(category);
		session.setAttribute("message","Category added successfully!!");
		return "redirect:/admin/admin-dashboard";
	}

	
	@PostMapping("/add-vehicle")
	public String addVehicle(@ModelAttribute("vehicleDto") VehicleDto vehicleDto,@RequestParam("vehicleImage") MultipartFile file,HttpSession session) throws IOException
	{
		
		//uploading file
		if(file.isEmpty())
		{
			//empty file
			vehicleDto.setImage("default-car.png");
		}
		else {
		   //upload file in a folder and save in contact
			vehicleDto.setImage(file.getOriginalFilename());
			File folderLocation = new ClassPathResource("static/images").getFile();
			//input stream,path where write data,copy
			Path path = Paths.get(folderLocation.getAbsolutePath()+File.separator+file.getOriginalFilename());
			Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
			System.out.println("file uploaded successfully!!");
		}
		Vehicle vehicle = this.modelMapper.map(vehicleDto, Vehicle.class);
		this.vehicleRepo.save(vehicle);
		session.setAttribute("message","Vehicle added successfully!!");
		return "redirect:/admin/admin-dashboard";
		
	}
	
	
	@GetMapping("/category-update/{categoryId}")
	public String UpdateCategory(@PathVariable("categoryId")Integer categoryId,Model model)
	{
		Category category = this.categoryRepo.findById(categoryId).get();
		model.addAttribute("category", category);
		return "/admin/category-update";
		
	}
	
//	@PostMapping("/update-category")
//	public String updateCategory(@ModelAttribute("categoryDto")CategoryDto categoryDto,HttpSession session,@RequestParam("categoryImage")MultipartFile file) throws IOException
//	{
//		//uploading file
//				if(file.isEmpty())
//				{
//					//empty file
//					categoryDto.setImage("default-car.png");
//				}
//				else {
//				   //upload file in a folder and save in contact
//					categoryDto.setImage(file.getOriginalFilename());
//					File folderLocation = new ClassPathResource("static/images").getFile();
//					//input stream,path where write data,copy
//					Path path = Paths.get(folderLocation.getAbsolutePath()+File.separator+file.getOriginalFilename());
//					Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
//					System.out.println("file uploaded successfully!!");
//				}
//				
//		Category category = this.modelMapper.map(categoryDto, Category.class);
//		this.categoryRepo.save(category);
//		session.setAttribute("message","Category added successfully!!");
//		return "redirect:/admin/admin-dashboard";
//	}
//	
	
	@GetMapping("/category-delete/{categoryId}")
	public String deleteCatgory(@PathVariable("categoryId")Integer categoryId)
	{
		Category category = this.categoryRepo.findById(categoryId).get();
		this.categoryRepo.delete(category);
		return "redirect:/admin/admin-dashboard";
	}
	
	
	@GetMapping("/vehicle-update/{vehicleId}")
	public String updateVehicle(@PathVariable("vehicleId")Integer vehicleId,Model model)
	{
		Vehicle vehicle = this.vehicleRepo.findById(vehicleId).get();
		model.addAttribute("vehicle", vehicle);
		List<Category> categories = this.categoryRepo.findAll();
		model.addAttribute("categories", categories);
		return "admin/vehicle-update";
	}
	
	@GetMapping("/vehicle-delete/{vehicleId}")
	public String deleteVehicle(@PathVariable("vehicleId")Integer vehicleId)
	{
		Vehicle vehicle = this.vehicleRepo.findById(vehicleId).get();
		this.vehicleRepo.delete(vehicle);
		return "redirect:/admin/admin-dashboard";
	}
	
}
