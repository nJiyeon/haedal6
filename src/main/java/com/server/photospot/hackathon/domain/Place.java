package com.server.photospot.hackathon.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name ="place")
@Getter
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name ="address", nullable = false)
    private String address;

    @Column(name="spring", nullable = false)
    private Boolean spring;

    @Column(name ="summer", nullable = false)
    private Boolean summer;

    @Column(name = "fall", nullable = false)
    private Boolean fall;

    @Column(name = "winter", nullable = false)
    private Boolean winter;

}