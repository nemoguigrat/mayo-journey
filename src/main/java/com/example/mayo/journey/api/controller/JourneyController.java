package com.example.mayo.journey.api.controller;

import com.example.mayo.journey.service.DocumentShortService;
import com.example.mayo.journey.service.dto.ListResponse;
import com.example.mayo.journey.service.dto.journey.DocumentShortFilter;
import com.example.mayo.journey.service.dto.journey.DocumentShortResponse;
import com.example.mayo.journey.service.dto.journey.DocumentShortWithPlacemarks;
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
import org.springframework.web.bind.annotation.PathVariable;

import static lombok.AccessLevel.PRIVATE;

@WebApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class JourneyController {

    DocumentShortService documentShortService;

    @GetMapping("/journey/all")
    @Operation(summary = "Список маршрутов с пагинацией")
    public ListResponse<DocumentShortResponse> findAll(@Parameter(hidden = true) @AuthenticationPrincipal MayoUserDetails user, @ParameterObject DocumentShortFilter filter, @ParameterObject Pageable pageable) {
        return documentShortService.findAll(user, pageable, filter);
    }

    @GetMapping("/journey/short/{id}")
    @Operation(summary = "Предпросмотр маршрута")
    public DocumentShortWithPlacemarks findWithPlacemarks(@Parameter(hidden = true) @AuthenticationPrincipal MayoUserDetails user, @PathVariable Long id) {
        return documentShortService.findWithPlacemarks(user, id);
    }
}
