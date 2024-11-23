package com.server.photospot.hackathon.dto.res;

import lombok.Builder;

@Builder
public record PlaceDetailResponse(
        String name,
        String address,
        Boolean spring,
        Boolean summer,
        Boolean fall,
        Boolean winter,
        String imageData
) {
}
