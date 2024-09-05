package com.etaxi.core.location;

import lombok.Builder;

@Builder
public record LocationPair(Double longitude, Double latitude) {
}
