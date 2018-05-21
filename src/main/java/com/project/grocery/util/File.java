package com.project.grocery.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.project.grocery.exception.NotFoundException;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 20, 2018
 * 
 */
public class File {

	private static final Logger LOG = LoggerFactory.getLogger(File.class);

	private static String UPLOADED_FOLDER = "E://Workspace//college//grocery//images";

	public static String fileWrite(MultipartFile file) {
		String imageUrl = UPLOADED_FOLDER + file.getOriginalFilename();
		LOG.debug("Write file to folder");
		if (file.isEmpty()) {

			throw new NotFoundException("File not found");
		}
		try {

			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageUrl;
	}
}
