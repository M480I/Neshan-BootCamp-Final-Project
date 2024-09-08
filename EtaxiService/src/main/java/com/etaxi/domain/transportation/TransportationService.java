package com.etaxi.domain.transportation;

import com.etaxi.core.exception.EntityNotFoundException;
import com.etaxi.core.redis.RedisProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

        RBucket<String> bucket =
                redissonClient.getBucket("transportationCache:" + title);

        Transportation cachedTransportation = null;

        try {
            cachedTransportation =
                    objectMapper.readValue(bucket.get(), Transportation.class);
        }
        catch (Exception ignored) {}

        if (cachedTransportation != null) {
            log.info("transportation" + title + "entity was found in cache");
            return cachedTransportation;
        }

        Optional<Transportation> optionalTransportation
                = transportationRepository.findByTitle(title);

        if (optionalTransportation.isPresent()) {
            Transportation transportation = optionalTransportation.get();
            try {
                bucket.set(
                        objectMapper.writeValueAsString(transportation),
                        Duration.ofSeconds(redisProperties.getTtl()));
            }
            catch (Exception ignored) {}
            return transportation;
        }

        throw new EntityNotFoundException("Transportation not found");

    }

}
