package com.etaxi.domain.transportation;

import com.etaxi.core.exception.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransportationService {

    TransportationRepository transportationRepository;

    public Transportation loadByTitle(String title) {

        Optional<Transportation> transportation = transportationRepository.findByTitle(title);
        if (transportation.isPresent())
            return transportation.get();
        throw new EntityNotFoundException("Transportation not found");

    }

}
