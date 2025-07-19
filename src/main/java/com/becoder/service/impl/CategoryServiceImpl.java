package com.becoder.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.CategoryRepository;
import com.becoder.services.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final ModelMapper modelMapper;
	private final CategoryRepository cateRepository;

	@Override
	public boolean savecategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		category.setIsDeleted(false);
		category.setCreatedOn(new Date());
		Category saveCategory = cateRepository.save(category);
		if (ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> list = cateRepository.findByIsDeletedFalse();

		List<CategoryDto> dtoList = list.stream().map(cat -> modelMapper.map(cat, CategoryDto.class)).toList();

//	    List<CategoryDto> dtoList = new ArrayList<>();
//	    for (Category category : list) {
//	        CategoryDto dto = modelMapper.map(category, CategoryDto.class);
//	        dtoList.add(dto);
//	    }
		return dtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		List<Category> activecategory = cateRepository.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> dtoList = activecategory.stream()
				.map(cat -> modelMapper.map(cat, CategoryResponse.class)).toList();
		return dtoList;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException {
	Category category = cateRepository.findByIdAndIsDeletedFalse(id)
				.orElseThrow(()-> new ResourceNotFoundException("category not found"+id));
		CategoryDto map = modelMapper.map(category, CategoryDto.class);
		return map;
	}

	@Override
	public boolean deleteById(Integer id) {
		Optional<Category> categoryById = cateRepository.findById(id);
		if (categoryById.isPresent()) {
			Category category = categoryById.get();
			category.setIsDeleted(true);
			cateRepository.save(category);
			return true;
		}
		return false;
	}

	@Override
	public CategoryDto updateCategory(Integer id, CategoryDto categoryDto) {
		Category existing = cateRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
		existing.setName(categoryDto.getName());
		existing.setDescription(categoryDto.getDescription());
		existing.setUpdatedOn(new Date());
		Category updatedCategory = cateRepository.save(existing);
		CategoryDto updatedCategoryDto = modelMapper.map(updatedCategory, CategoryDto.class);

		return updatedCategoryDto;
	}

}
