package org.zerock.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Controller

@Log4j
public class UploadController {
	
	@RequestMapping("/upload")
	public void upload(MultipartFile[] files) {
		for (MultipartFile file : files) {
			log.info(file.getOriginalFilename());
			log.info(file.getContentType());
			log.info(file.getSize());
			
			File saveFile = new File("C:\\upload\\" + file.getOriginalFilename());
			
			try {
				file.transferTo(saveFile);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
