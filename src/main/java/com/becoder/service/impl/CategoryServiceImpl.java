package com.becoder.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.repository.CategoryRepository;
import com.becoder.services.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	
	private final ModelMapper modelMapper;
	private final CategoryRepository cateRepository;

	@Override
	public boolean savecategory(CategoryDto categoryDto) {	
		Category  category = modelMapper.map(categoryDto, Category.class);	
		Category saveCategory = cateRepository.save(category);
		if(ObjectUtils.isEmpty(saveCategory)){
			return false;
		}
		return true;
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> list = cateRepository.findAll();

		List<CategoryDto> dtoList = list.stream().map(cat-> modelMapper.map(cat,CategoryDto.class)).toList();
		
//	    List<CategoryDto> dtoList = new ArrayList<>();
//	    for (Category category : list) {
//	        CategoryDto dto = modelMapper.map(category, CategoryDto.class);
//	        dtoList.add(dto);
//	    }
		return dtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		List<Category> activecategory=	cateRepository.findByIsActiveTrue();
		List<CategoryResponse> dtoList = activecategory.stream().map(cat-> modelMapper.map(cat,CategoryResponse.class)).toList();
		return dtoList;
	}

}
