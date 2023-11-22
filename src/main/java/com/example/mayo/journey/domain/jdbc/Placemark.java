package com.example.mayo.journey.domain.jdbc;

import com.example.mayo.journey.support.PlacemarkType;
import com.example.mayo.journey.support.domain.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Placemark extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_info_id", referencedColumnName = "id")
    AttachmentInfo attachmentInfo;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    DocumentIndex documentIndex;

    @Column
    String name;

    @Column
    String description;

    @Column
    String address;

    @Column
    String longitude;

    @Column
    String latitude;

    @Enumerated(EnumType.STRING)
    PlacemarkType type;

    @Column
    Boolean publicMark;
}
