package com.example.mayo.journey.service.impl;

import com.example.mayo.journey.domain.jdbc.Theme;
import com.example.mayo.journey.repository.jdbc.ThemeRepository;
import com.example.mayo.journey.service.ThemeService;
import com.example.mayo.journey.service.dto.ListResponse;
import com.example.mayo.journey.service.dto.theme.ThemeResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ThemeServiceImpl implements ThemeService {

    ThemeRepository themeRepository;

    @Override
    public ListResponse<ThemeResponse> findAll(Pageable pageable) {
        Page<Theme> page = themeRepository.findAllThemes(pageable);
        return ListResponse.of(page.map(this::buildThemeResponse));
    }

    private ThemeResponse buildThemeResponse(Theme theme) {
        return ThemeResponse.builder()
                .name(theme.getName())
                .description(theme.getDescription())
                .build();
    }
}
