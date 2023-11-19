package com.example.mayo.journey.repository.jdbc;

import com.example.mayo.journey.domain.jdbc.DocumentShort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentShortRepository extends JpaRepository<DocumentShort, Long>, JpaSpecificationExecutor<DocumentShort> {
}
