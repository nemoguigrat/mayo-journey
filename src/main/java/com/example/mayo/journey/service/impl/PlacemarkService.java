package com.example.mayo.journey.service.impl;

import com.example.mayo.journey.domain.jdbc.Placemark;
import com.example.mayo.journey.repository.jdbc.PlacemarkRepository;
import com.example.mayo.journey.repository.jdbc.UserRepository;
import com.example.mayo.journey.service.IPlacemarkService;
import com.example.mayo.journey.service.dto.ListResponse;
import com.example.mayo.journey.service.dto.journey.PlacemarkFullData;
import com.example.mayo.journey.service.dto.journey.PlacemarkShortResponse;
import com.example.mayo.journey.support.MayoUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PlacemarkService implements IPlacemarkService {

    PlacemarkRepository placemarkRepository;
    UserRepository userRepository;

    @Override
    public ListResponse<PlacemarkShortResponse> findAll(Pageable pageable) {
        //TODO: Remove try-catch
        try {
            var page = placemarkRepository.findByPublicMark(true, pageable);
            return ListResponse.of(page.map(this::buildPlacemarkShort));
        }
        catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ListResponse<PlacemarkShortResponse> findAllByUser(MayoUserDetails user, Pageable pageable) {
        //TODO: Remove try-catch
        try {
            var page = placemarkRepository.findByPublicMarkAndUser(false, userRepository.findUserById(user.getId()), pageable);
            return ListResponse.of(page.map(this::buildPlacemarkShort));
        }
        catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Optional<PlacemarkFullData> findPlacemark(Long id) {
        var placemark = placemarkRepository.findById(id).orElse(null);
        if (placemark == null) return Optional.empty();

        return Optional.ofNullable(buildPlacemarkFull(placemark));
    }

    @Override
    public Placemark createPlacemark(MayoUserDetails owner, PlacemarkFullData placemark) {
        var pm = buildPlacemarkFromDTO(placemark);
        pm.setUser(userRepository.findUserById(owner.getId()));
        placemarkRepository.save(pm);
        return pm;
    }

    @Override
    public Placemark updatePlacemark(Long id, PlacemarkFullData placemark) {
        var existingPlacemark = placemarkRepository.findById(id).orElse(null);
        if (existingPlacemark == null)
        {
            return null;
        }
        return updatePlacemarkWithDTO(existingPlacemark, placemark);
    }

    private PlacemarkShortResponse buildPlacemarkShort(Placemark placemark) {
        return PlacemarkShortResponse.builder()
                .id(placemark.getId())
                .name(placemark.getName())
                .longtitude(placemark.getLongitude())
                .latitude(placemark.getLatitude())
                .build();
    }

    private PlacemarkShortResponse buildPlacemarkShort(PlacemarkFullData placemark){
        return PlacemarkShortResponse.builder()
                .id(placemark.getId())
                .name(placemark.getName())
                .longtitude(placemark.getLongitude())
                .latitude(placemark.getLatitude())
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
            // Fuck it
            if (data.getName() != null) {
                target.setName(data.getName());
            }
            if (data.getDescription() != null) {
                target.setDescription(data.getDescription());
            }
            if (data.getAddress() != null) {
                target.setAddress(data.getAddress());
            }
            if (data.getLongitude() != null) {
                target.setLongitude(data.getLongitude());
            }
            if (data.getLatitude() != null) {
                target.setLatitude(data.getLatitude());
            }
            if (data.getType() != null) {
                target.setType(data.getType());
            }
            if (data.getPublicMark() != null) {
                target.setPublicMark(data.getPublicMark());
            }

            return placemarkRepository.save(target);
        }
        return null;
    }
}
