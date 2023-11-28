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
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
}
