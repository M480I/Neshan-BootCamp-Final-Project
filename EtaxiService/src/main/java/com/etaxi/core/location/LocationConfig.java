package com.etaxi.core.location;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocationConfig {

    @Bean
    public PrecisionModel getPrecisionModel() {
        return new PrecisionModel(PrecisionModel.FLOATING);
    }

    @Bean
    public GeometryFactory getGeometryFactory(PrecisionModel model) {
        return new GeometryFactory(model, 4326);
    }

}
