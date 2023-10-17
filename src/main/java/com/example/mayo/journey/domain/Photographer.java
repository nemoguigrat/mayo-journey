package com.example.mayo.journey.domain;

import com.example.mayo.journey.support.domain.BaseEntity;
import com.example.mayo.journey.support.domain.DataType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Photographer extends BaseEntity {

    @Column
    String firstname;

    @Column
    String surname;

    @Column
    String middleName;

    @Column
    LocalDate birthdate;

    @Column
    String phone;

    @Type(type = DataType.JSONB)
    @Column(name = "contacts", columnDefinition = DataType.JSONB)
    Map<String, String> contacts;

    @Column
    String email;

    @Column
    boolean trainee;

    @Column
    String status;

    @Column
    String description;

    @Column
    Integer score;

    @Lob
    @Column
    byte[] image;

    @OneToOne(mappedBy = "photographer")
    User user;
}
