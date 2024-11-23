package com.server.photospot.hackathon.dto.res;

import lombok.Builder;

@Builder
public record PlacesResponse(
        Double latitude,
        Double longitude
) {
}
