package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.UploadDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller

@Log4j
public class UploadController {

	@PostMapping(value="/upload", produces="application/json; charset=UTF-8")
	@ResponseBody
	public List<UploadDTO> upload(MultipartFile[] files) {

		List<UploadDTO> result = new ArrayList<>();

		for (MultipartFile file : files) {
			log.info(file.getOriginalFilename());
			log.info(file.getContentType());
			log.info(file.getSize());

			UUID uuid = UUID.randomUUID();

			String saveFileName = uuid.toString() + "_" + file.getOriginalFilename();
			String thumbFileName = "s_" + saveFileName;

			File saveFile = new File("C:\\upload\\" + saveFileName);
			FileOutputStream thumbFile = null;

			try {

				thumbFile = new FileOutputStream("C:\\upload\\" + thumbFileName);

				Thumbnailator.createThumbnail(file.getInputStream(), thumbFile, 100, 100);

				thumbFile.close();
				file.transferTo(saveFile);
				result.add(new UploadDTO(saveFileName,
						file.getOriginalFilename(),
						thumbFileName, true));

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return result;
	}
}
