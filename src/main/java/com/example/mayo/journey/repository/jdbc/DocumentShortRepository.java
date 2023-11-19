package com.example.mayo.journey.repository.jdbc;

import com.example.mayo.journey.domain.jdbc.DocumentShort;
import com.example.mayo.journey.support.DocumentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentShortRepository extends JpaRepository<DocumentShort, Long> {

    @Query(value = "select distinct ds from DocumentShort ds where ds.documentIndex.status = :status and " +
            "(:themeId is null or (:themeId is not null and ds.documentIndex.theme.id = :themeId)) ",
            countQuery = "select count(ds.id) from DocumentShort ds where ds.documentIndex.status = :status and " +
                    "(:themeId is null or (:themeId is not null and ds.documentIndex.theme.id = :themeId)) ")
    Page<DocumentShort> findByFilter(DocumentStatus status, Long themeId, Pageable pageable);

    @Query(value = "select distinct ds from DocumentShort ds where ds.documentIndex.user.id = :userId ",
            countQuery = "select count(ds.id) from DocumentShort ds where ds.documentIndex.user.id = :userId")
    Page<DocumentShort> findByUser(Long userId, Pageable pageable);
}
