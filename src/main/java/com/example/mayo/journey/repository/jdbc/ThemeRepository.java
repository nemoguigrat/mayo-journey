package com.example.mayo.journey.repository.jdbc;

import com.example.mayo.journey.domain.jdbc.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    @Query(value = "select distinct t from Theme t",
            countQuery = "select count(t.id) from Theme t")
    Page<Theme> findAllThemes(Pageable pageable);
}
