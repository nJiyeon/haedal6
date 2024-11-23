package com.server.photospot.hackathon.service;

import com.server.photospot.hackathon.domain.Place;
import com.server.photospot.hackathon.dto.req.PlacesRequest;
import com.server.photospot.hackathon.dto.res.PlaceDetailResponse;
import com.server.photospot.hackathon.dto.res.PlacesResponse;
import com.server.photospot.hackathon.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    //private final Path imageDir = Paths.get(System.getProperty("user.dir"),"src/main/resources/image");

    public List<PlacesResponse> findPlaceByFourSeasons(PlacesRequest request){
        Set<PlacesResponse> placesResponses = new HashSet<>();
        List<Place> places = new ArrayList<>();

        if(request.spring()){
            places = placeRepository.findAllBySpring(true);
        }
        if(request.summer()){
            places = placeRepository.findAllBySummer(true);
        }
        if(request.fall()){
            places = placeRepository.findAllByFall(true);
        }
        if(request.winter()){
            places = placeRepository.findAllByWinter(true);
        }

        placesResponses = places.stream()
                .map(place -> new PlacesResponse(place.getLatitude(), place.getLongitude()))
                .collect(Collectors.toSet());

        return placesResponses.stream().collect(Collectors.toList());
    }

    public PlaceDetailResponse placeDetail(Float latitude, Float longitude){
        PlaceDetailResponse response = null;
        return response;
    }
}
