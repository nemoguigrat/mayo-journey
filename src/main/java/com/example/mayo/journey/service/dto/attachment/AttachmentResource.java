package com.example.mayo.journey.service.dto.attachment;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentResource {

    Resource content;

    String contentType;
}
