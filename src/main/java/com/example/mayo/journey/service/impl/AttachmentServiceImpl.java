package com.example.mayo.journey.service.impl;

import com.example.mayo.journey.config.properties.MayoFilesProperties;
import com.example.mayo.journey.domain.jdbc.Attachment;
import com.example.mayo.journey.exception.NotFoundException;
import com.example.mayo.journey.repository.jdbc.AttachmentRepository;
import com.example.mayo.journey.service.AttachmentService;
import com.example.mayo.journey.service.dto.attachment.AttachmentResource;
import com.example.mayo.journey.support.MayoUserDetails;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.tika.Tika;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AttachmentServiceImpl implements AttachmentService {

    Path baseStoragePath;
    AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(MayoFilesProperties mayoFilesProperties, AttachmentRepository repository) {
        this.baseStoragePath = Paths.get(mayoFilesProperties.getFilePath()).toAbsolutePath().normalize();
        this.attachmentRepository = repository;

        init();
    }

    private void init() {
        try {
            Files.createDirectories(this.baseStoragePath);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public AttachmentResource download(MayoUserDetails user, Long id) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        try {
            Path filePath = baseStoragePath.resolve(Path.of(attachment.getPath()).normalize());

            Resource resource = new PathResource(filePath);
            return AttachmentResource.builder()
                    .content(resource)
                    .contentType(attachment.getContentType())
                    .build();

        } catch (Exception e) {

            throw new NotFoundException();
        }


    }

    @Override
    @Transactional
    public Attachment upload(MultipartFile file) {
        String fileName = UUID.randomUUID().toString();

        try {
            Path datePath = pathByDate();
            Path relativePath = datePath.resolve(fileName);
            Path targetLocation = this.baseStoragePath.resolve(datePath);

            if (!Files.exists(targetLocation)) {
                Files.createDirectories(this.baseStoragePath.resolve(targetLocation));
            }

            long size = Files.copy(file.getInputStream(), targetLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            String ext = resolveContentType(file.getBytes(), fileName);

            Attachment attachment = Attachment.builder()
                    .path(relativePath.toString())
                    .size(size)
                    .contentType(ext)
                    .build();

            return attachmentRepository.save(attachment);

        } catch (IOException ex) {
            throw new IllegalArgumentException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    private static Path pathByDate() {
        LocalDate today = LocalDate.now();

        String year = String.valueOf(today.getYear());
        String month = String.valueOf(today.getMonthValue());
        String day = String.valueOf(today.getDayOfMonth());

        return Path.of(year, month, day).normalize();
    }

    public static String resolveContentType(byte[] content, String filename) {
        Tika tika = new Tika();

        return tika.detect(content, filename);
    }

}
