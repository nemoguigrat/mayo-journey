package com.example.mayo.journey.service;

import com.example.mayo.journey.domain.jdbc.Attachment;
import com.example.mayo.journey.service.dto.attachment.AttachmentResource;
import com.example.mayo.journey.support.MayoUserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

    AttachmentResource download(MayoUserDetails user, Long id);

    Attachment upload(MultipartFile file);
}
