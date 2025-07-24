package com.becoder.service.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesDto.CategoryDto;
import com.becoder.dto.NotesResponse;
import com.becoder.entity.FileDetails;
import com.becoder.entity.Notes;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.CategoryRepository;
import com.becoder.repository.FileRepository;
import com.becoder.repository.NotesRepository;
import com.becoder.services.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotesServiceImpl implements NotesService {

	private final NotesRepository notesRepository;
	
	private final CategoryRepository categoryRepository;
	
	private final ModelMapper mapper;
	
	
	@Value("${file.upload.path}")
	private String uploadpath;

	@Autowired
	private FileRepository fileRepo;
	
	
	
	
	
	
	
	
	@Override
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception  {
	
		ObjectMapper ob = new ObjectMapper();
		NotesDto notesDto = ob.readValue(notes, NotesDto.class);

		// category validation
	//	checkCategoryExist(notesDto.getCategory());

		Notes notesMap = mapper.map(notesDto, Notes.class);

		FileDetails fileDtls = saveFileDetails(file);

		if (!ObjectUtils.isEmpty(fileDtls)) {
			notesMap.setFileDetails(fileDtls);		
		} else {
			notesMap.setFileDetails(null);
		}
			
		Notes save = notesRepository.save(notesMap);
		if(ObjectUtils.isEmpty(save)) {
			return false;
		}
		return true;
	}
	

	private FileDetails saveFileDetails(MultipartFile file) throws IOException {

		if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {

			String originalFilename = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFilename);

			List<String> extensionAllow = Arrays.asList("pdf", "xlsx", "jpg", "png");
			if (!extensionAllow.contains(extension)) {
				throw new IllegalArgumentException("invalid file format ! Upload only .pdf , .xlsx,.jpg");
			}

			String rndString = UUID.randomUUID().toString();
			String uploadfileName = rndString + "." + extension; // sdfsafbhkljsf.pdf

			File saveFile = new File(uploadpath);
			if (!saveFile.exists()) {
				saveFile.mkdir();
			}
			// path : enotesapiservice/notes/java.pdf
			String storePath = uploadpath.concat(uploadfileName);

			// upload file
			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			if (upload != 0) {
				FileDetails fileDtls = new FileDetails();
				fileDtls.setOriginalFileName(originalFilename);
				fileDtls.setDisplayFileName(getDisplayName(originalFilename));
				fileDtls.setUploadFileName(uploadfileName);
				fileDtls.setFileSize(file.getSize());
				fileDtls.setPath(storePath);
				FileDetails saveFileDtls = fileRepo.save(fileDtls);
				return saveFileDtls;
			}
		}

		return null;
	}

	private String getDisplayName(String originalFilename) {
		// java_programming_tutorials.pdf
		// java_prog.pdf
		String extension = FilenameUtils.getExtension(originalFilename);
		String fileName = FilenameUtils.removeExtension(originalFilename);

		if (fileName.length() > 8) {
			fileName = fileName.substring(0, 7);
		}
		fileName = fileName + "." + extension;
		return fileName;
	}


	@Override
	public List<NotesDto> getAllNotes() {
		List<NotesDto> list = notesRepository.findAll().stream().map(notes-> mapper.map(notes, NotesDto.class)).toList();
		return list;
	}
	
	
	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws Exception {

		InputStream io = new FileInputStream(fileDetails.getPath());

		return StreamUtils.copyToByteArray(io);
	}

	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		FileDetails fileDtls = fileRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("File is not available"));
		return fileDtls;
	}


	@Override
	public NotesResponse getNotesByUser(Integer pageNo,Integer userId, Integer pageSize) {
		
		Pageable pageable =  PageRequest.of(pageNo, pageSize);
           Page<Notes> pageNotes=	notesRepository.findByCreatedBy(userId ,pageable);
           
           List<NotesDto> notesDto = pageNotes.get().map(n-> mapper.map(n,NotesDto.class)).toList();
           
           
           NotesResponse notes = NotesResponse.builder().notes(notesDto).pageNo(pageNotes.getNumber())
   				.pageSize(pageNotes.getSize()).totalElements(pageNotes.getTotalElements())
   				.totalPages(pageNotes.getTotalPages()).isFirst(pageNotes.isFirst()).isLast(pageNotes.isLast()).build();

           return notes;
	}
	

}
