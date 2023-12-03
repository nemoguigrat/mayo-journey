package com.example.mayo.journey.service.dto.journey;

import com.example.mayo.journey.service.dto.placemark.PlacemarkShortResponse;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class DocumentShortWithPlacemarks {

    Long id;

    Long documentIndexId;

    String name;

    String description;

    String address;

    List<PlacemarkShortResponse> placemarks;
}
