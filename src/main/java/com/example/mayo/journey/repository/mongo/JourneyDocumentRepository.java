package com.example.mayo.journey.repository.mongo;

import com.example.mayo.journey.domain.mongo.JourneyDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JourneyDocumentRepository extends MongoRepository<JourneyDocument, UUID> {
}
