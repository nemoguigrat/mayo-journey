package com.example.mayo.journey.repository.jdbc;

import com.example.mayo.journey.domain.jdbc.Placemark;
import com.example.mayo.journey.domain.jdbc.User;
import com.example.mayo.journey.support.MayoUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacemarkRepository extends JpaRepository<Placemark, Long> {
    Page<Placemark> findByPublicMark(Boolean pub, Pageable pageable);

    Page<Placemark> findByPublicMarkAndUser(Boolean publicMark, User user, Pageable pageable);
}
