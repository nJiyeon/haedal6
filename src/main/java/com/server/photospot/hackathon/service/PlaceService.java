package com.server.photospot.hackathon.service;

import com.server.photospot.hackathon.dto.req.PlacesRequest;
import com.server.photospot.hackathon.dto.res.PlaceDetailResponse;
import com.server.photospot.hackathon.dto.res.PlacesResponse;
import com.server.photospot.hackathon.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlacesResponse findPlaceByFourSeasons(PlacesRequest request){
        PlacesResponse response = null;
        return response;
    }

    public PlaceDetailResponse placeDetail(Float latitude, Float longitude){
        PlaceDetailResponse response = null;
        return response;
    }
}
