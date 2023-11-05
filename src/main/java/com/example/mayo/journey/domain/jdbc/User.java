package com.example.mayo.journey.domain.jdbc;

import com.example.mayo.journey.support.UserStatus;
import com.example.mayo.journey.support.domain.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column
    String email;

    @Column
    String password;

    @Column(name = "reg_date")
    LocalDate registrationDate;

    @Column
    Boolean blocked;

    @Column
    LocalDateTime lastLoginTime;

    @Column
    @Enumerated(EnumType.STRING)
    UserStatus status;
}
