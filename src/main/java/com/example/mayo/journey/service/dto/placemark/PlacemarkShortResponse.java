package com.example.mayo.journey.service.dto.placemark;

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

    String longitude;

    String latitude;

    Long attachmentId;
}
