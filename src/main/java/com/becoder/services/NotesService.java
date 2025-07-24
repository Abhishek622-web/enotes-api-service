package com.becoder.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesResponse;
import com.becoder.entity.FileDetails;

public interface NotesService {

	public Boolean saveNotes(String notes,MultipartFile file) throws Exception;
	public List<NotesDto> getAllNotes();
	
	public byte[] downloadFile(FileDetails fileDtls) throws Exception;

	public FileDetails getFileDetails(Integer id) throws Exception;
	public NotesResponse getNotesByUser(Integer pageNo, Integer pageSize, Integer pageSize2);
}
