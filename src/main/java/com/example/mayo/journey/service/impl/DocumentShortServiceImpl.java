package com.example.mayo.journey.service.impl;

import com.example.mayo.journey.domain.jdbc.DocumentShort;
import com.example.mayo.journey.domain.jdbc.Placemark;
import com.example.mayo.journey.exception.NotFoundException;
import com.example.mayo.journey.repository.jdbc.DocumentShortRepository;
import com.example.mayo.journey.repository.jdbc.PlacemarkRepository;
import com.example.mayo.journey.repository.jdbc.specification.DocumentShortSpec;
import com.example.mayo.journey.service.DocumentShortService;
import com.example.mayo.journey.service.dto.ListResponse;
import com.example.mayo.journey.service.dto.journey.DocumentShortFilter;
import com.example.mayo.journey.service.dto.journey.DocumentShortResponse;
import com.example.mayo.journey.service.dto.journey.DocumentShortWithPlacemarks;
import com.example.mayo.journey.service.dto.placemark.PlacemarkShortResponse;
import com.example.mayo.journey.support.MayoUserDetails;
import com.example.mayo.journey.support.domain.BaseEntity;
import com.example.mayo.journey.support.utils.NullSafeUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DocumentShortServiceImpl implements DocumentShortService {

    DocumentShortRepository documentShortRepository;
    PlacemarkRepository placemarkRepository;

    @Override
    public ListResponse<DocumentShortResponse> findAll(MayoUserDetails user, Pageable pageable, DocumentShortFilter filter) {
        Page<DocumentShort> page = documentShortRepository.findAll(DocumentShortSpec.documentShortFilter(user, filter), pageable);

        return ListResponse.of(page.map(this::buildDocumentShort));
    }

    @Override
    public DocumentShortWithPlacemarks findWithPlacemarks(MayoUserDetails user, Long id) {
        DocumentShort documentShort = documentShortRepository.findAndFetchAttachments(id).orElseThrow(NotFoundException::new);
        List<Placemark> placemarks = placemarkRepository.findByDocumentIndex(documentShort.getDocumentIndex());

        return DocumentShortWithPlacemarks.builder()
                .id(documentShort.getId())
                .documentIndexId(documentShort.getDocumentIndex().getId())
                .attachments(documentShort.getAttachmentInfo().getAttachments().stream().map(BaseEntity::getId).collect(Collectors.toList()))
                .address(documentShort.getAddress())
                .name(documentShort.getName())
                .placemarks(placemarks.stream().map(this::buildPlacemarkShort).collect(Collectors.toList()))
                .build();
    }

    private DocumentShortResponse buildDocumentShort(DocumentShort documentShort) {
        return DocumentShortResponse.builder()
                .id(documentShort.getId())
                .address(documentShort.getAddress())
                .description(documentShort.getDescription())
                .documentIndexId(documentShort.getDocumentIndex().getId())
                .attachments(documentShort.getAttachmentInfo().getAttachments().stream().map(BaseEntity::getId).collect(Collectors.toList()))
                .name(documentShort.getName())
                .build();
    }

    private PlacemarkShortResponse buildPlacemarkShort(Placemark placemark) {
        return PlacemarkShortResponse.builder()
                .id(placemark.getId())
                .name(placemark.getName())
                .longtitude(placemark.getLongitude())
                .latitude(placemark.getLatitude())
                .attachmentId(NullSafeUtils.safeGetId(placemark.getAttachment()))
                .build();
    }
}
