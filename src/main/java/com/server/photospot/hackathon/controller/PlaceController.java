package com.server.photospot.hackathon.controller;

import com.server.photospot.hackathon.dto.req.PlacesRequest;
import com.server.photospot.hackathon.dto.res.PlaceDetailResponse;
import com.server.photospot.hackathon.dto.res.PlacesResponse;
import com.server.photospot.hackathon.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping("/api/getPlaces")
    public ResponseEntity<List<PlacesResponse>> getPlaces(@RequestBody PlacesRequest request){
        List<PlacesResponse> responses = placeService.findPlaceByFourSeasons(request);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/api/getPlaceDetail")
    public ResponseEntity<PlaceDetailResponse> getPlaceDetail(
        @RequestParam Double latitude, @RequestParam Double longitude
    ){
        PlaceDetailResponse response = placeService.placeDetail(latitude, longitude);
        return ResponseEntity.ok(response);
    }
}
