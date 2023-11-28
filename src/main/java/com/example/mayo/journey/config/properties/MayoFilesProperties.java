package com.example.mayo.journey.config.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mayo")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class MayoFilesProperties {

    String filePath;
}
