package com.server.photospot.hackathon.dto.res;

import lombok.Builder;

@Builder
public record PlaceDetailResponse(
        String name,
        String address,
        String imgUrl
) {
}
