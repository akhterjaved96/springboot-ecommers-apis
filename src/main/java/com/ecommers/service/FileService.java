package com.ecommers.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
	
	//uploading file
    String uploadImage(String path, MultipartFile file) throws Exception;
	
    //serving file
	InputStream getResource(String path, String fileName) throws FileNotFoundException;

	//delete file if exists
    boolean deleteFileIfExists(String filePath) throws IOException;

}
