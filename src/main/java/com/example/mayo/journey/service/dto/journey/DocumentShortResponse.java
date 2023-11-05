package com.example.mayo.journey.service.dto.journey;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class DocumentShortResponse {

    Long attachmentInfoId;

    Long documentIndexId;

    String name;

    String description;

    String address;
}
