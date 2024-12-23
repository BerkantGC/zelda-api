package com.gc.zelda_api.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtils {
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveImageFile(MultipartFile file, String salt) throws IOException {
        String contentType = file.getContentType();
        if (!("image/png".equals(contentType)
                || "image/jpeg".equals(contentType)
                || "image/jpg".equals(contentType)
                || "image/webp".equals(contentType)
        )) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "File isn't supported");
        }

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String uniqueName = createUniqueImageName(salt);
        BufferedImage image = ImageIO.read(file.getInputStream());

        // Save image as .jpg
        File jpgFile = new File(uploadPath + "/" + uniqueName + ".jpg");
        ImageIO.write(image, "jpg", jpgFile);

        return uniqueName;
    }

    //salt is a key string to combine with generated random uuid string
    public String createUniqueImageName(String salt) {
        return salt + "_" + UUID.randomUUID();
    }
}
