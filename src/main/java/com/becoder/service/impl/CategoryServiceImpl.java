package com.becoder.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.becoder.entity.Category;
import com.becoder.repository.CategoryRepository;
import com.becoder.services.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryRepository cateRepository;

	@Override
	public boolean savecategory(Category category) {
		category.setIsDeleted(false);
		Category saveCategory = cateRepository.save(category);
		if(ObjectUtils.isEmpty(saveCategory)){
			return false;
		}
		return true;
	}

	@Override
	public List<Category> getAllCategory() {
		List<Category> list = cateRepository.findAll();
		return list;
	}

}
