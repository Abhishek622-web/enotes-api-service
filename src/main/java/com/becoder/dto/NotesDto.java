package com.becoder.dto;

import java.util.Date;

import com.becoder.entity.Category;

import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotesDto {

	private Integer id;
	private String title;
	private String description;
	private CategoryDto category;
	private Integer createdBy;
	private Integer updatedBy;
	private Date createdOn;
	private Date updatedOn;
	
	private FilesDto fileDetails;

	@Getter
	@Setter
	@NoArgsConstructor
	public static class FilesDto {
		private Integer id;
		private String originalFileName;
		private String displayFileName;
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class CategoryDto{    // we are this class because we want only 
		private Integer id;             // this much data in response
		private String name;           //if we want whole o/p then remove this class it will
	}
}
