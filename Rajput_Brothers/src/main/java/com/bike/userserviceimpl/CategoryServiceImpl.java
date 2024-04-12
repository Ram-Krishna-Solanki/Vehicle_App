package com.bike.userserviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bike.dto.CategoryDto;
import com.bike.entities.Category;
import com.bike.exceptions.ResourceNotFoundException;
import com.bike.repositories.CategoryRepo;
import com.bike.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category category = this.modelMapper.map(categoryDto,Category.class);
		Category categorySaved = this.categoryRepo.save(category);
		return this.modelMapper.map(categorySaved, CategoryDto.class);
	}

	@Override
	public void updateCategory(CategoryDto categoryDto, Integer categoryId) {
		// TODO Auto-generated method stub
       Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","id",categoryId));
       category.setTitle(categoryDto.getTitle());
       category.setDescription(categoryDto.getDescription()); 
       this.categoryRepo.save(category);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","id", categoryId));
        this.categoryRepo.delete(category);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		// TODO Auto-generated method stub
		List<Category> categories = this.categoryRepo.findAll();
	    List<CategoryDto> categoriesDto = categories.stream().map(category->this.modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
		return categoriesDto;
	}

}
