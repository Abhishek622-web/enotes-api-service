package com.becoder.dto;

import java.util.Date;

import com.becoder.entity.Category;
import com.becoder.entity.FileDetails;

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
	private FileDetails fileDetails;

	
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class CategoryDto{    // we are this class because we want only 
		private Integer id;             // this much data in response
		private String name;           //if we want whole o/p then remove this class it will
	}
	@Getter
	@Setter
	@NoArgsConstructor
	public static class FileDetails {
		private Integer id;
		private String originalFileName;
		private String displayFileName;
	}
}
