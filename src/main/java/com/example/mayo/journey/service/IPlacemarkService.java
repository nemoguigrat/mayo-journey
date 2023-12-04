package com.example.mayo.journey.service;

import com.example.mayo.journey.domain.jdbc.Placemark;
import com.example.mayo.journey.service.dto.ListResponse;
import com.example.mayo.journey.service.dto.placemark.PlacemarkFullData;
import com.example.mayo.journey.service.dto.placemark.PlacemarkShortResponse;
import com.example.mayo.journey.support.MayoUserDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface IPlacemarkService
{
    ListResponse<PlacemarkShortResponse> findAll(Pageable pageable);
    ListResponse<PlacemarkShortResponse> findNearest(double latitude, double longitude, Pageable pageable);
    ListResponse<PlacemarkShortResponse> findAllByUser(MayoUserDetails user, Pageable pageable);

    Optional<PlacemarkFullData> findPlacemark(Long placemarkId);

    Placemark createPlacemark(MayoUserDetails owner, PlacemarkFullData placemark);

    Placemark updatePlacemark(MayoUserDetails owner, Long id, PlacemarkFullData placemark);

    Long updatePlacemarkImage(MayoUserDetails user, Long id, MultipartFile file);
}
