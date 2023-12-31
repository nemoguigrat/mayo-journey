package com.example.mayo.journey.repository.jdbc;

import com.example.mayo.journey.domain.jdbc.DocumentIndex;
import com.example.mayo.journey.domain.jdbc.Placemark;
import com.example.mayo.journey.domain.jdbc.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlacemarkRepository extends JpaRepository<Placemark, Long> {
    Page<Placemark> findByPublicMark(Boolean pub, Pageable pageable);

    @Query(value = "SELECT p.*, " +
            "6371 * acos(cos(radians(:inputLat)) * cos(radians(CAST(p.latitude AS DOUBLE PRECISION))) * " +
            "cos(radians(CAST(p.longitude AS DOUBLE PRECISION))) - radians(:inputLon) + " +
            "sin(radians(:inputLat)) * sin(radians(CAST(p.latitude AS DOUBLE PRECISION)))) AS distance " +
            "FROM Placemark p " +
            "ORDER BY distance", nativeQuery = true)
    Page<Placemark> findNearestPlacemarks(@Param("inputLat") double latitude, @Param("inputLon") double longitude, Pageable pageable);

    Page<Placemark> findByPublicMarkAndUser(Boolean publicMark, User user, Pageable pageable);

    List<Placemark> findByDocumentIndex(DocumentIndex documentIndex);

    Optional<Placemark> findByUser_IdAndId(Long userId, Long id);
}
