package com.example.mayo.journey.service.impl;

import com.example.mayo.journey.domain.jdbc.DocumentShort;
import com.example.mayo.journey.repository.jdbc.DocumentShortRepository;
import com.example.mayo.journey.repository.jdbc.UserRepository;
import com.example.mayo.journey.service.DocumentShortService;
import com.example.mayo.journey.service.dto.ListResponse;
import com.example.mayo.journey.service.dto.journey.DocumentShortFilter;
import com.example.mayo.journey.service.dto.journey.DocumentShortResponse;
import com.example.mayo.journey.support.DocumentStatus;
import com.example.mayo.journey.support.MayoUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DocumentShortServiceImpl implements DocumentShortService {

    DocumentShortRepository documentShortRepository;
    UserRepository userRepository;

    @Override
    public ListResponse<DocumentShortResponse> findAll(MayoUserDetails user, Pageable pageable, DocumentShortFilter filter) {
        Page<DocumentShort> page;

        if (filter.getStatus() == DocumentStatus.DRAFT) {
            page = documentShortRepository.findByUser(user.getId(), pageable);
        } else {
            page = documentShortRepository.findByFilter(filter.getStatus(), filter.getThemeId(), pageable);
        }

        return ListResponse.of(page.map(this::buildDocumentShort));
    }

    private DocumentShortResponse buildDocumentShort(DocumentShort documentShort) {
        return DocumentShortResponse.builder()
                .address(documentShort.getAddress())
                .description(documentShort.getDescription())
                .name(documentShort.getName())
                .build();
    }
}
