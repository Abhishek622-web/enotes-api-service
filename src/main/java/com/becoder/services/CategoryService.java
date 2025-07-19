package com.becoder.services;

import java.util.List;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.exception.ResourceNotFoundException;

public interface CategoryService {

	
	public boolean savecategory(CategoryDto categoryDto);
	public List<CategoryDto> getAllCategory();
	public List<CategoryResponse> getActiveCategory();
	public CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException;
	public boolean deleteById(Integer id);
	public CategoryDto updateCategory(Integer id,CategoryDto categoryDto);
}
