package com.example.mayo.journey.domain.jdbc;

import com.example.mayo.journey.support.domain.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;

@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
public class DocumentShort extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "attachment_info_id", referencedColumnName = "id")
    AttachmentInfo attachmentInfo;

    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    DocumentIndex documentIndex;

    @Column
    String name;

    @Column
    String description;

    @Column
    String address;
}
