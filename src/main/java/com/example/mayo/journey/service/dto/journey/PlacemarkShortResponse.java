package com.example.mayo.journey.service.dto.journey;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class PlacemarkShortResponse {
    Long id;
    String name;
    String longtitude;
    String latitude;
}
