package com.example.mayo.journey.domain.jdbc;

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
public class Attachment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "attachment_info_id", referencedColumnName = "id")
    AttachmentInfo attachmentInfo;

    @Column
    String contentType;

    @Column
    String path;

    @Column
    Long size;
}
