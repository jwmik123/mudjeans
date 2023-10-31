package com.team3.mudjeans.services;

import com.sun.xml.bind.api.impl.NameConverter;
import com.team3.mudjeans.exceptions.StorageException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class StorageService {
    private static final String storageLocation = "src/main/resources/static/uploads";

    private final Path fileStorageLocationObject;

    public StorageService() {
        fileStorageLocationObject = Paths.get(storageLocation).toAbsolutePath().normalize();
        if (!fileStorageLocationObject.toFile().exists()) {
            if (fileStorageLocationObject.toFile().mkdirs()) {
                System.out.println("created dirs");
            }
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {

            Path targetLocation = fileStorageLocationObject.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new StorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }

    public File loadFile(String filename) {
        Path targetLocation = fileStorageLocationObject.resolve(filename);
        return new File(String.valueOf(targetLocation));
    }

    public Resource downloadFile(String fileName) {
        try {
            Path targetLocation = fileStorageLocationObject.resolve(fileName);
            return new ByteArrayResource(Files.readAllBytes(targetLocation));
        } catch (IOException e) {
            throw new StorageException("Could not find file " + fileName + ". Please try again!", e);
        }
    }
}
