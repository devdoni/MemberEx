package com.movie.test.Member.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Log4j2
@Service
public class UploadFileService {
    public String upload(String loginedMemberID, MultipartFile file) {
        log.info("upload()");

        boolean result = false;

        // File 저장
        String fileOriName = file.getOriginalFilename(); // aaaa.jpg
        String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length());
        String uploadDir = "C:\\member\\profile\\";

        UUID uuid = UUID.randomUUID();
        String uniqueName = uuid.toString().replaceAll("-", "");

        File saveFile = new File(uploadDir + loginedMemberID + "\\" + uniqueName + fileExtension);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }

        try {
            file.transferTo(saveFile);
            result = true;

        } catch (Exception e) {
            e.printStackTrace();

        }

        if (result) {
            log.info("PROFILE IMAGE UPLOAD SUCCESS!!");

            return uniqueName + fileExtension;

        } else {
            log.info("PROFILE IMAGE UPLOAD FILE!!");

            return null;

        }

    }
}
