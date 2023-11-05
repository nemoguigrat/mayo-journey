package com.example.mayo.journey.config;

import com.example.mayo.journey.support.domain.BaseDocument;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.UUID;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.mayo.journey.repository.mongo")
public class MongoConfig {

    @Bean
    public BeforeConvertCallback<BaseDocument> beforeSaveCallback() {

        return (entity, collection) -> {

            if (entity.getId() == null) {
                entity.setId(UUID.randomUUID());
            }
            return entity;
        };
    }
}
