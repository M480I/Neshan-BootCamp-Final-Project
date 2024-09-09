package com.etaxi.core.location.validation;

import com.etaxi.core.location.LocationPair;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LocationPairValidator implements ConstraintValidator<LocationPairValidation, LocationPair> {

    @Override
    public void initialize(LocationPairValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocationPair locationPair, ConstraintValidatorContext context) {
        if (locationPair == null) {
            return true;
        }

        double latitude = locationPair.latitude();
        double longitude = locationPair.longitude();

        return latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180;
    }
}
