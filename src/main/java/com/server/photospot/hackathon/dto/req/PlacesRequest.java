package com.server.photospot.hackathon.dto.req;

import lombok.Builder;

@Builder
public record PlacesRequest(
        Boolean spring,
        Boolean summer,
        Boolean fall,
        Boolean winter
) {
}
