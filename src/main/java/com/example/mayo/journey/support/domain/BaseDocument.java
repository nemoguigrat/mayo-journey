package com.example.mayo.journey.support.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
@FieldNameConstants
@AllArgsConstructor
@Getter
public abstract class BaseDocument {

    @Id
    UUID id;

    public void setId(UUID id) {

        if (this.id != null) {
            throw new UnsupportedOperationException("ID is already defined");
        }

        this.id = id;
    }
}
