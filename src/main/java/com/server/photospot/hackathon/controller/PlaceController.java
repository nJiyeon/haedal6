package com.server.photospot.hackathon.controller;

import com.server.photospot.hackathon.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;


    @GetMapping("/api/getPlaces")
    public ResponseEntity<?> getPlaces(

    ){

        return ResponseEntity.ok("");
    }

    @GetMapping("/api/getPlaceDetail")
    public ResponseEntity<?> getPlaceDetail(

    ){

        return ResponseEntity.ok("");
    }

}
