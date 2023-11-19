package com.example.mayo.journey.service.dto.journey;

import com.example.mayo.journey.support.DocumentStatus;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class DocumentShortFilter {

    Long themeId;

    DocumentStatus status;
}
