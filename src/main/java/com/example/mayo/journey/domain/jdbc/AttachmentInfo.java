package com.example.mayo.journey.domain.jdbc;

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
@AllArgsConstructor
@FieldNameConstants
public class AttachmentInfo extends BaseEntity {

    @GeneratedValue(generator = "uuid")
    String objectId;

    @OneToMany(mappedBy = "attachmentInfo")
    Set<Attachment> attachments = new HashSet<>();
}
