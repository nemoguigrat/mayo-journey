package com.example.mayo.journey.repository.jdbc;

import com.example.mayo.journey.domain.jdbc.DocumentIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentIndexRepository extends JpaRepository<DocumentIndex, Long> {
}
