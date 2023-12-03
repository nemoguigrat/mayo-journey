package com.example.mayo.journey.service.dto.journey;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class DocumentShortResponse {

    Long id;

    Long placemarkAttachmentId;

    Long documentIndexId;

    String name;

    String description;

    String address;
}
