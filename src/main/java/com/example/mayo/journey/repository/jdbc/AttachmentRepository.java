package com.example.mayo.journey.repository.jdbc;

import com.example.mayo.journey.domain.jdbc.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
