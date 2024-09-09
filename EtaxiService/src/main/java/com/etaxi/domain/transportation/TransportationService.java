package com.etaxi.domain.transportation;

import com.etaxi.core.exception.EntityNotFoundException;
import com.etaxi.core.redis.RedisProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log
public class TransportationService {

    TransportationRepository transportationRepository;
    RedissonClient redissonClient;
    RedisProperties redisProperties;
    ObjectMapper objectMapper;


    public Transportation loadByTitle(String title) {

        Optional<Transportation> optionalTransportation
                = transportationRepository.findByTitle(title);

        if (optionalTransportation.isPresent()) {
            return optionalTransportation.get();
        }
        throw new EntityNotFoundException("Transportation not found");

    }

}
