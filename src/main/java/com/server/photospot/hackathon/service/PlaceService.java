package com.server.photospot.hackathon.service;

import com.server.photospot.hackathon.domain.Place;
import com.server.photospot.hackathon.dto.req.PlacesRequest;
import com.server.photospot.hackathon.dto.res.PlaceDetailResponse;
import com.server.photospot.hackathon.dto.res.PlacesResponse;
import com.server.photospot.hackathon.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final Path imageDir = Paths.get(System.getProperty("user.dir"),"src/main/resources/static/");

    public List<PlacesResponse> findPlaceByFourSeasons(PlacesRequest request){
        List<Place> places = new ArrayList<>();

        if(request.spring()){
            places.addAll(placeRepository.findAllBySpring(true));
        }
        if(request.summer()){
            places.addAll(placeRepository.findAllBySummer(true));
        }
        if(request.fall()){
            places.addAll(placeRepository.findAllByFall(true));
        }
        if(request.winter()){
            places.addAll(placeRepository.findAllByWinter(true));
        }

        Set<PlacesResponse> placesResponses = places.stream()
                .map(place -> new PlacesResponse(place.getLatitude(), place.getLongitude()))
                .collect(Collectors.toSet());

        return placesResponses.stream().collect(Collectors.toList());
    }

    public PlaceDetailResponse placeDetail(Double latitude, Double longitude){
        Place place = placeRepository.findByLatitudeAndLongitude(latitude, longitude);
        String imgUrl = encodingImageToBase64(System.getProperty("user.dir") + "/src/main/resources/static/"+ place.getLatitude().toString() + "_" + place.getLongitude().toString() + ".jpg");
        System.out.println(imgUrl);
        return new PlaceDetailResponse(
                place.getName(),
                place.getAddress(),
                place.getSpring(),
                place.getSummer(),
                place.getFall(),
                place.getWinter(),
                imgUrl
        );
    }

    public String encodingImageToBase64(String imagePath){
        System.out.println(imagePath);
        try{
            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch(IOException e){
            return null;
        }
    }
}