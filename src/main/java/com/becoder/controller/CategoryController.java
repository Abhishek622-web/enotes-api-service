package com.becoder.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.services.CategoryService;
import com.becoder.util.CommonUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@PostMapping("/save")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {

		boolean savecategory = categoryService.savecategory(categoryDto);
		if (savecategory) {

			return CommonUtil.createBuildResponseMessage("category saved", HttpStatus.CREATED);
			// return new ResponseEntity<>("category saved", HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponseMessage("category not saved", HttpStatus.INTERNAL_SERVER_ERROR);

		// return new ResponseEntity<>("category not saved",
		// HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/")
	public ResponseEntity<?> getAllCategories() {
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
		// return new ResponseEntity<>(allCategory, HttpStatus.OK);
	}

	@GetMapping("/active")
	public ResponseEntity<?> getActiveCategories() {
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();
		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}
		return new ResponseEntity<>(allCategory, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getDetailsById(@PathVariable Integer id) throws ResourceNotFoundException {
		CategoryDto categoryById = categoryService.getCategoryById(id);
		if (ObjectUtils.isEmpty(categoryById)) {
			return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(categoryById, HttpStatus.FOUND);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id) {
		boolean categoryDeleted = categoryService.deleteById(id);
		if (categoryDeleted) {
			return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("Category not found", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateById(@PathVariable Integer id, @RequestBody CategoryDto categoryDto) {

		CategoryDto updateCategoryDto = categoryService.updateCategory(id, categoryDto);
		return new ResponseEntity<>(updateCategoryDto, HttpStatus.OK);
	}

}
