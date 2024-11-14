package com.server.photospot.hackathon.repository;

import com.server.photospot.hackathon.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place,Long> {
}
