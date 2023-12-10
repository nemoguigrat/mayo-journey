package com.example.mayo.journey.service.impl;

import com.example.mayo.journey.domain.jdbc.DocumentIndex;
import com.example.mayo.journey.domain.jdbc.DocumentShort;
import com.example.mayo.journey.domain.jdbc.Placemark;
import com.example.mayo.journey.domain.jdbc.Theme;
import com.example.mayo.journey.exception.NotFoundException;
import com.example.mayo.journey.repository.jdbc.*;
import com.example.mayo.journey.repository.jdbc.specification.DocumentShortSpec;
import com.example.mayo.journey.service.DocumentShortService;
import com.example.mayo.journey.service.dto.ListResponse;
import com.example.mayo.journey.service.dto.journey.DocumentShortFilter;
import com.example.mayo.journey.service.dto.journey.DocumentShortRequest;
import com.example.mayo.journey.service.dto.journey.DocumentShortResponse;
import com.example.mayo.journey.service.dto.journey.DocumentShortWithPlacemarks;
import com.example.mayo.journey.service.dto.placemark.PlacemarkShortResponse;
import com.example.mayo.journey.support.MayoUserDetails;
import com.example.mayo.journey.support.utils.NullSafeUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DocumentShortServiceImpl implements DocumentShortService {

    DocumentShortRepository documentShortRepository;
    PlacemarkRepository placemarkRepository;
    UserRepository userRepository;
    ThemeRepository themeRepository;
    DocumentIndexRepository documentIndexRepository;

    @Override
    @Transactional(readOnly = true)
    public ListResponse<DocumentShortResponse> findAll(MayoUserDetails user, Pageable pageable, DocumentShortFilter filter) {
        Page<DocumentShort> page = documentShortRepository.findAll(DocumentShortSpec.documentShortFilter(user, filter), pageable);

        return ListResponse.of(page.map(this::buildDocumentShort));
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentShortWithPlacemarks findWithPlacemarks(MayoUserDetails user, Long id) {
        DocumentShort documentShort = documentShortRepository.findAndFetchAttachments(id).orElseThrow(NotFoundException::new);
        List<Placemark> placemarks = placemarkRepository.findByDocumentIndex(documentShort.getDocumentIndex());
        Optional<DocumentIndex> index = Optional.ofNullable(documentShort.getDocumentIndex());

        DocumentShortWithPlacemarks.DocumentShortWithPlacemarksBuilder builder = DocumentShortWithPlacemarks.builder()
                .id(documentShort.getId())
                .documentIndexId(documentShort.getDocumentIndex().getId())
                .themeId(documentShort.getDocumentIndex().getTheme().getId())
                .address(documentShort.getAddress())
                .name(documentShort.getName())
                .placemarks(placemarks.stream().map(this::buildPlacemarkShort).collect(Collectors.toList()));

        index.ifPresent(documentIndex -> builder.documentIndexId(documentIndex.getId())
                .themeId(NullSafeUtils.safeGetId(documentIndex.getTheme())));

        return builder.build();
    }

    @Override
    @Transactional
    public void createDocument(MayoUserDetails user, DocumentShortRequest request) {
       DocumentIndex index = DocumentIndex.builder()
                .documentId(UUID.randomUUID().toString())
                .status(request.getStatus())
                .user(userRepository.getReferenceById(user.getId()))
                .theme(request.getThemeId() != null ? themeRepository.getReferenceById(request.getThemeId()) : null)
               .build();

       DocumentShort shortD = DocumentShort.builder()
                .documentIndex(index)
                .name(request.getName())
                .description(request.getDescription())
                .address(request.getAddress())
                .build();

       documentIndexRepository.save(index);
       documentShortRepository.save(shortD);
    }

    @Override
    @Transactional
    public void updateDocument(MayoUserDetails user, Long id, DocumentShortRequest request) {
        DocumentShort shortD = documentShortRepository.findByIdAndFetchDocumentIndexThemes(id).orElseThrow(NotFoundException::new);
        Theme theme = request.getThemeId() != null ? themeRepository.getReferenceById(request.getThemeId()) : null;
        shortD.updateFromRequest(request);

        shortD.getDocumentIndex().setTheme(theme);
    }

    @Override
    @Transactional
    public void linkPlacemark(MayoUserDetails user, Long documentId, Long placemarkId) {
        DocumentIndex index = documentIndexRepository.findById(documentId).orElseThrow(NotFoundException::new);

        index.getPlacemarks().add(placemarkRepository.getReferenceById(placemarkId));
    }

    private DocumentShortResponse buildDocumentShort(DocumentShort documentShort) {
        Optional<DocumentIndex> index = Optional.ofNullable(documentShort.getDocumentIndex());

        DocumentShortResponse.DocumentShortResponseBuilder builder = DocumentShortResponse.builder()
                .id(documentShort.getId())
                .address(documentShort.getAddress())
                .description(documentShort.getDescription())
                .name(documentShort.getName());

        if (index.isPresent()) {
            Long attachId = index.get().getPlacemarks().stream().map(x -> NullSafeUtils.safeGetId(x.getAttachment())).findFirst().orElse(null);
            builder.documentIndexId(index.get().getId())
                    .placemarkAttachmentId(attachId);
        }

        return builder.build();
    }

    private PlacemarkShortResponse buildPlacemarkShort(Placemark placemark) {
        return PlacemarkShortResponse.builder()
                .id(placemark.getId())
                .name(placemark.getName())
                .longitude(placemark.getLongitude())
                .latitude(placemark.getLatitude())
                .attachmentId(NullSafeUtils.safeGetId(placemark.getAttachment()))
                .build();
    }
}
