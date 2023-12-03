package com.example.mayo.journey.domain.jdbc;

import com.example.mayo.journey.support.DocumentStatus;
import com.example.mayo.journey.support.domain.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
@NoArgsConstructor
@FieldNameConstants
@AllArgsConstructor
public class DocumentIndex extends BaseEntity {

    String documentId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @ManyToOne
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    Theme theme;

    @OneToMany(mappedBy = "documentIndex")
    Set<Placemark> placemarks = new HashSet<>();

    @Enumerated(EnumType.STRING)
    DocumentStatus status;
}
