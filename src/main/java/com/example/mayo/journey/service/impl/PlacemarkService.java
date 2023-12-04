package com.example.mayo.journey.service.impl;

import com.example.mayo.journey.domain.jdbc.Attachment;
import com.example.mayo.journey.domain.jdbc.Placemark;
import com.example.mayo.journey.exception.NotFoundException;
import com.example.mayo.journey.repository.jdbc.PlacemarkRepository;
import com.example.mayo.journey.repository.jdbc.UserRepository;
import com.example.mayo.journey.service.AttachmentService;
import com.example.mayo.journey.service.IPlacemarkService;
import com.example.mayo.journey.service.dto.ListResponse;
import com.example.mayo.journey.service.dto.placemark.PlacemarkFullData;
import com.example.mayo.journey.service.dto.placemark.PlacemarkShortResponse;
import com.example.mayo.journey.support.MayoUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.example.mayo.journey.support.utils.NullSafeUtils.safeGetId;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PlacemarkService implements IPlacemarkService {

    PlacemarkRepository placemarkRepository;
    UserRepository userRepository;
    AttachmentService attachmentService;

    @Override
    @Transactional(readOnly = true)
    public ListResponse<PlacemarkShortResponse> findAll(Pageable pageable) {
        Page<Placemark> page = placemarkRepository.findByPublicMark(true, pageable);
        return ListResponse.of(page.map(this::buildPlacemarkShort));
    }

    @Override
    @Transactional(readOnly = true)
    public ListResponse<PlacemarkShortResponse> findAllByUser(MayoUserDetails user, Pageable pageable) {
        Page<Placemark> page = placemarkRepository.findByPublicMarkAndUser(false, userRepository.findUserById(user.getId()), pageable);
        return ListResponse.of(page.map(this::buildPlacemarkShort));
    }

    @Override
    @Transactional(readOnly = true)
    public ListResponse<PlacemarkShortResponse> findNearest(double latitude, double longitude, Pageable pageable) {
        Page<Placemark> page = placemarkRepository.findNearestPlacemarks(latitude, longitude, pageable);
        return ListResponse.of(page.map(this::buildPlacemarkShort));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlacemarkFullData> findPlacemark(Long id) {
        Placemark placemark = placemarkRepository.findById(id).orElse(null);
        if (placemark == null) return Optional.empty();

        return Optional.ofNullable(buildPlacemarkFull(placemark));
    }

    @Override
    @Transactional
    public Placemark createPlacemark(MayoUserDetails owner, PlacemarkFullData placemarkData) {
        Placemark placemark = buildPlacemarkFromDTO(placemarkData);
        placemark.setUser(userRepository.findUserById(owner.getId()));
        placemarkRepository.save(placemark);
        return placemark;
    }

    @Override
    @Transactional
    public Placemark updatePlacemark(MayoUserDetails owner, Long id, PlacemarkFullData placemark) {
        Placemark existingPlacemark = placemarkRepository.findByUser_IdAndId(owner.getId(), id).orElseThrow(NotFoundException::new);

        return updatePlacemarkWithDTO(existingPlacemark, placemark);
    }

    @Override
    @Transactional
    public Long updatePlacemarkImage(MayoUserDetails user, Long id, MultipartFile file) {
        Placemark placemark = placemarkRepository.findByUser_IdAndId(user.getId(), id).orElseThrow(NotFoundException::new);

        Attachment attachment = attachmentService.upload(file);

        placemark.setAttachment(attachment);

        return attachment.getId();
    }

    private PlacemarkShortResponse buildPlacemarkShort(Placemark placemark) {
        return PlacemarkShortResponse.builder()
                .id(placemark.getId())
                .name(placemark.getName())
                .longitude(placemark.getLongitude())
                .latitude(placemark.getLatitude())
                .attachmentId(safeGetId(placemark.getAttachment()))
                .build();
    }

    private PlacemarkFullData buildPlacemarkFull(Placemark placemark) {
        return PlacemarkFullData.builder()
                .name(placemark.getName())
                .description(placemark.getDescription())
                .address(placemark.getAddress())
                .longitude(placemark.getLongitude())
                .latitude(placemark.getLatitude())
                .type(placemark.getType())
                .publicMark(placemark.getPublicMark())
                .attachmentId(safeGetId(placemark.getAttachment()))
                .build();
    }

    private Placemark buildPlacemarkFromDTO(PlacemarkFullData placemark) {
        return Placemark.builder()
                .name(placemark.getName())
                .description(placemark.getDescription())
                .address(placemark.getAddress())
                .longitude(placemark.getLongitude())
                .latitude(placemark.getLatitude())
                .type(placemark.getType())
                .publicMark(placemark.getPublicMark())
                .build();
    }

    private Placemark updatePlacemarkWithDTO(Placemark target, PlacemarkFullData data) {
        if (target != null) {
            target.setName(data.getName());
            target.setDescription(data.getDescription());
            target.setAddress(data.getAddress());
            target.setLongitude(data.getLongitude());
            target.setLatitude(data.getLatitude());
            target.setType(data.getType());
            target.setPublicMark(data.getPublicMark());

            return placemarkRepository.save(target);
        }
        return null;
    }
}
