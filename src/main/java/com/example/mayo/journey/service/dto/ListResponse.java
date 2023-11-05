package com.example.mayo.journey.service.dto;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
@Builder
public class ListResponse<T> {

    List<T> list;

    Long count;

    public static <T> ListResponse<T> of(Page<T> page) {
        return new ListResponse<>(page.getContent(), page.getTotalElements());
    }
}
