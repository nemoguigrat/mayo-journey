package com.example.mayo.journey.service;

import com.example.mayo.journey.service.dto.attachment.AttachmentResource;
import com.example.mayo.journey.support.MayoUserDetails;

public interface AttachmentService {

    AttachmentResource download(MayoUserDetails user, Long id);
}
