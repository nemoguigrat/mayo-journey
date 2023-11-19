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
public class Theme extends BaseEntity {

    @Column
    String name;

    @Column
    String description;

    @Column(name = "public")
    Boolean publicTheme;
}
