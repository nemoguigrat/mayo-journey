package com.example.mayo.journey.service;

import com.example.mayo.journey.domain.jdbc.Placemark;
import com.example.mayo.journey.service.dto.ListResponse;
import com.example.mayo.journey.service.dto.journey.PlacemarkFullData;
import com.example.mayo.journey.service.dto.journey.PlacemarkShortResponse;
import com.example.mayo.journey.support.MayoUserDetails;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IPlacemarkService
{
    ListResponse<PlacemarkShortResponse> findAll(Pageable pageable);

    ListResponse<PlacemarkShortResponse> findAllByUser(MayoUserDetails user, Pageable pageable);

    Optional<PlacemarkFullData> findPlacemark(Long placemarkId);

    Placemark createPlacemark(MayoUserDetails owner, PlacemarkFullData placemark);

    Placemark updatePlacemark(Long id, PlacemarkFullData placemark);
}
