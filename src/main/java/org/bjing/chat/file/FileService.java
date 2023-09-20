package org.bjing.chat.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    private final Path rootLocation;

    FileService(@Value("${files.save-path}") String savePath) {
        this.rootLocation = Paths.get(savePath);
    }

    public FileCreatedResponse saveFileLocally(MultipartFile file) {
        try {
            if (file.isEmpty() || file.getOriginalFilename() == null) {
                throw new IllegalArgumentException("File empty");
            }

            Path destinationFile = rootLocation.resolve(file.getOriginalFilename());
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                System.out.printf("File saved: %s\n", destinationFile);
                return new FileCreatedResponse(destinationFile.toString(),destinationFile.toFile().getName(), file.getSize());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

}
