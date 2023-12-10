package com.example.mayo.journey.repository.jdbc;

import com.example.mayo.journey.domain.jdbc.DocumentShort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentShortRepository extends JpaRepository<DocumentShort, Long>, JpaSpecificationExecutor<DocumentShort> {

    @Query("select distinct d from DocumentShort d left join fetch d.attachmentInfo as at left join fetch at.attachments where d.id = :id")
    Optional<DocumentShort> findAndFetchAttachments(Long id);

    @Query("select distinct d from DocumentShort d left join fetch d.documentIndex as di left join fetch di.theme where d.id = :id")
    Optional<DocumentShort> findByIdAndFetchDocumentIndexThemes(Long id);
}
