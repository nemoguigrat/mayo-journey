package com.example.mayo.journey.api.controller;


import com.example.mayo.journey.service.IPlacemarkService;
import com.example.mayo.journey.service.dto.ListResponse;
import com.example.mayo.journey.service.dto.journey.PlacemarkFullData;
import com.example.mayo.journey.service.dto.journey.PlacemarkShortResponse;
import com.example.mayo.journey.support.MayoUserDetails;
import com.example.mayo.journey.support.api.WebApi;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@WebApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PlacemarksController {

    IPlacemarkService placemarkService;

    @GetMapping("/placemark/all")
    public ListResponse<PlacemarkShortResponse> getAll(@Parameter(hidden = true) @AuthenticationPrincipal MayoUserDetails user, @RequestParam boolean isPublic, @ParameterObject Pageable pageable) {
            if (isPublic)
                return placemarkService.findAll(pageable);
            return placemarkService.findAllByUser(user, pageable);
    }

    @GetMapping("/placemark/{id}")
    public PlacemarkFullData getPlacemark(@PathVariable Long id) {
        return placemarkService.findPlacemark(id).orElse(null);
    }

    @PostMapping("/placemark/create")
    public ResponseEntity<?> createPlacemark(@Parameter(hidden = true) @AuthenticationPrincipal MayoUserDetails userAuthor, @RequestBody PlacemarkFullData placemarkData) {
        if (placemarkService.createPlacemark(userAuthor, placemarkData) != null)
            return ResponseEntity.ok().build();

        return ResponseEntity.status(500).build();
    }

    @PutMapping("placemark/update/{id}")
    public ResponseEntity<?> updatePlacemark(@Parameter(hidden = true) @AuthenticationPrincipal MayoUserDetails user, @PathVariable Long id, @RequestBody PlacemarkFullData data) {
        if (placemarkService.updatePlacemark(id, data) != null)
            return ResponseEntity.ok().build();

        return ResponseEntity.status(500).build();
    }
}
