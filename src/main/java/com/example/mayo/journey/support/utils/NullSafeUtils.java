package com.example.mayo.journey.support.utils;

import com.example.mayo.journey.support.domain.BaseEntity;

import java.util.Optional;

public class NullSafeUtils {

    public static Long safeGetId(BaseEntity object) {
        return Optional.ofNullable(object).map(BaseEntity::getId).orElse(null);
    }
}
