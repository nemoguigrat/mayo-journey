package com.example.mayo.journey.service.dto.journey;

import com.example.mayo.journey.domain.jdbc.AttachmentInfo;
import com.example.mayo.journey.domain.jdbc.DocumentIndex;
import com.example.mayo.journey.support.PlacemarkType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class PlacemarkFullData {
    Long id;
    Long user_id;
    String name;
    String description;
    String address;
    String longitude;
    String latitude;
    PlacemarkType type;
    Boolean publicMark;
}
