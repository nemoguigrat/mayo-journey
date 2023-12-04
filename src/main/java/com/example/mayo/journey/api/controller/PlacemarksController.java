package com.example.mayo.journey.api.controller;


import com.example.mayo.journey.service.IPlacemarkService;
import com.example.mayo.journey.service.dto.ListResponse;
import com.example.mayo.journey.service.dto.placemark.PlacemarkFullData;
import com.example.mayo.journey.service.dto.placemark.PlacemarkShortResponse;
import com.example.mayo.journey.support.MayoUserDetails;
import com.example.mayo.journey.support.api.WebApi;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/placemark/nearest")
    public ListResponse<PlacemarkShortResponse> getNearest(@RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude, @ParameterObject Pageable pageable) {
        try{
            return placemarkService.findNearest(Double.parseDouble(latitude), Double.parseDouble(longitude), pageable);
        }
        catch (Exception ex) {
            return null;
        }
    }

    @PostMapping("/placemark/create")
    public void createPlacemark(@Parameter(hidden = true) @AuthenticationPrincipal MayoUserDetails userAuthor, @RequestBody PlacemarkFullData placemarkData) {
        placemarkService.createPlacemark(userAuthor, placemarkData);
    }

    @PutMapping("/placemark/update/{id}")
    public void updatePlacemark(@Parameter(hidden = true) @AuthenticationPrincipal MayoUserDetails user, @PathVariable Long id, @RequestBody PlacemarkFullData data) {
        placemarkService.updatePlacemark(user, id, data);
    }

    @PostMapping("/placemark/image/{id}")
    public void updatePlacemarkImage(@Parameter(hidden = true) @AuthenticationPrincipal MayoUserDetails user, @PathVariable Long id, @RequestPart(name = "file") MultipartFile file) {
        placemarkService.updatePlacemarkImage(user, id, file);
    }
}
