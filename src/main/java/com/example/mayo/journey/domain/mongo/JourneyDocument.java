package com.example.mayo.journey.domain.mongo;

import com.example.mayo.journey.support.domain.BaseDocument;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "journeys")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JourneyDocument extends BaseDocument {

    private String someInfo;
}
