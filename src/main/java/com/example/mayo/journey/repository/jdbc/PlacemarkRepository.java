package com.example.mayo.journey.repository.jdbc;

import com.example.mayo.journey.domain.jdbc.DocumentIndex;
import com.example.mayo.journey.domain.jdbc.Placemark;
import com.example.mayo.journey.domain.jdbc.User;
import com.example.mayo.journey.support.MayoUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlacemarkRepository extends JpaRepository<Placemark, Long> {
    Page<Placemark> findByPublicMark(Boolean pub, Pageable pageable);

    Page<Placemark> findByPublicMarkAndUser(Boolean publicMark, User user, Pageable pageable);

    List<Placemark> findByDocumentIndex(DocumentIndex documentIndex);

    Optional<Placemark> findByUser_IdAndId(Long userId, Long id);
}
