package com.example.mayo.journey.service.dto.theme;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class ThemeResponse {

    String name;

    String description;
}
