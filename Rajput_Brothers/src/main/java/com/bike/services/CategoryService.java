package com.bike.services;

import java.util.List;

import com.bike.dto.CategoryDto;

public interface CategoryService {

	public abstract CategoryDto createCategory(CategoryDto categoryDto);
	
	public abstract void updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	public abstract void deleteCategory(Integer categoryId);
	
	public abstract List<CategoryDto> getAllCategory();
	
}
