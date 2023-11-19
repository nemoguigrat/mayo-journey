package com.example.mayo.journey.domain.jdbc;

import com.example.mayo.journey.support.DocumentStatus;
import com.example.mayo.journey.support.domain.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentIndex extends BaseEntity {

    String documentId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @ManyToOne
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    Theme theme;

    @Enumerated(EnumType.STRING)
    DocumentStatus status;
}