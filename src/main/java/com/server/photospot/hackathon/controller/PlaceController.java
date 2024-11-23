package com.server.photospot.hackathon.controller;

import com.server.photospot.hackathon.dto.req.PlacesRequest;
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
    public ResponseEntity<?> getPlaceDetail(
        @RequestParam Float latitude, @RequestParam Float Longitude
    ){
        // 서비스에서 호출할 예정
        return ResponseEntity.ok("");
    }

}
