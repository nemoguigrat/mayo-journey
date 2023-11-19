package com.example.mayo.journey.service;

import com.example.mayo.journey.service.dto.ListResponse;
import com.example.mayo.journey.service.dto.theme.ThemeResponse;
import org.springframework.data.domain.Pageable;

public interface ThemeService {

    ListResponse<ThemeResponse> findAll(Pageable pageable);
}
