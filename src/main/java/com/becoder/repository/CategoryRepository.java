package com.becoder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.becoder.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	

	List<Category> findByIsActiveTrueAndIsDeletedFalse();

	List<Category> findByIsDeletedFalse();

	Optional<Category> findByIdAndIsDeletedFalse(Integer id);

	boolean existsByName(String name);



	

}
