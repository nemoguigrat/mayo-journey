package com.example.mayo.journey.config;

import com.example.mayo.journey.config.properties.MayoFilesProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MayoFilesProperties.class)
public class MayoConfig {

}
