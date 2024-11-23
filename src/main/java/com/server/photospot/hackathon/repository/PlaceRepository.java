package com.server.photospot.hackathon.repository;

import com.server.photospot.hackathon.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place,Long> {
    List<Place> findAllBySpring(Boolean spring);
    List<Place> findAllBySummer(Boolean summer);
    List<Place> findAllByFall(Boolean fall);
    List<Place> findAllByWinter(Boolean winter);
}
