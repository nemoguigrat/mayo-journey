package com.example.mayo.journey.api.controller;

import com.example.mayo.journey.service.AttachmentService;
import com.example.mayo.journey.service.dto.attachment.AttachmentResource;
import com.example.mayo.journey.support.MayoUserDetails;
import com.example.mayo.journey.support.api.WebApi;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static lombok.AccessLevel.PRIVATE;

@WebApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AttachmentController {

    AttachmentService attachmentService;

    @GetMapping("/attachment/{id}")
    public ResponseEntity<Resource> download(@Parameter(hidden = true) @AuthenticationPrincipal MayoUserDetails userDetails,
                                             @PathVariable Long id) {
        AttachmentResource response = attachmentService.download(userDetails, id);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(response.getContentType()))
                .body(response.getContent());
    }
}
