package com.becoder.services;

import java.util.List;

import com.becoder.entity.Category;

public interface CategoryService {

	
	public boolean savecategory(Category category);
	public List<Category> getAllCategory();
}
