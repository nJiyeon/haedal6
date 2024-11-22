package com.server.photospot.hackathon.controller;

import com.server.photospot.hackathon.dto.req.PlacesRequest;
import com.server.photospot.hackathon.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;


    @PostMapping("/api/getPlaces")
    public ResponseEntity<?> getPlaces(@RequestBody PlacesRequest request){
        // 서비스에서 호출할 예정
        return ResponseEntity.ok("");
    }

    @GetMapping("/api/getPlaceDetail")
    public ResponseEntity<?> getPlaceDetail(
        @RequestParam Float latitude, @RequestParam Float Longitude
    ){
        // 서비스에서 호출할 예정
        return ResponseEntity.ok("");
    }

}
