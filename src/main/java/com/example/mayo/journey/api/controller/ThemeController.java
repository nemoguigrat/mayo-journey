package com.example.mayo.journey.api.controller;

import com.example.mayo.journey.service.ThemeService;
import com.example.mayo.journey.service.dto.ListResponse;
import com.example.mayo.journey.service.dto.theme.ThemeResponse;
import com.example.mayo.journey.support.MayoUserDetails;
import com.example.mayo.journey.support.api.WebApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

import static lombok.AccessLevel.PRIVATE;

@WebApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class ThemeController {

    ThemeService themeService;

    @GetMapping("/theme/all")
    @Operation(summary = "Список тем с пагинацией")
    public ListResponse<ThemeResponse> findAll(@Parameter(hidden = true) @AuthenticationPrincipal MayoUserDetails user, @ParameterObject Pageable pageable) {
        return themeService.findAll(pageable);
    }
}
