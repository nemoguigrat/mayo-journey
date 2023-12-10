package com.example.mayo.journey.service;

import com.example.mayo.journey.service.dto.ListResponse;
import com.example.mayo.journey.service.dto.journey.DocumentShortFilter;
import com.example.mayo.journey.service.dto.journey.DocumentShortRequest;
import com.example.mayo.journey.service.dto.journey.DocumentShortResponse;
import com.example.mayo.journey.service.dto.journey.DocumentShortWithPlacemarks;
import com.example.mayo.journey.support.MayoUserDetails;
import org.springframework.data.domain.Pageable;

public interface DocumentShortService {

    ListResponse<DocumentShortResponse> findAll(MayoUserDetails user, Pageable pageable, DocumentShortFilter filter);

    DocumentShortWithPlacemarks findWithPlacemarks(MayoUserDetails user, Long id);

    void createDocument(MayoUserDetails user, DocumentShortRequest request);

    void updateDocument(MayoUserDetails user, Long id, DocumentShortRequest request);

    void linkPlacemark(MayoUserDetails user, Long documentId, Long placemarkId);
}
