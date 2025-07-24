package com.becoder.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotesResponse {

	List<NotesDto> notes;
	private Integer pageNo;
	private Integer pageSize;
	private Long totalElements;
	private Integer totalPages;
	private boolean isFirst;
	private boolean isLast;
	
	public NotesResponse(List<NotesDto> notes, Integer pageNo, Integer pageSize, Long totalElements,
			Integer totalPages, boolean isFirst, boolean isLast) {
		super();
		this.notes = notes;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.isFirst = isFirst;
		this.isLast = isLast;
	}
	
	
}
