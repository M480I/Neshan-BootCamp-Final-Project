package com.logger.log;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogService {

    LogRepository logRepository;

    public void storeLog(Log log) {
        logRepository.save(log);
    }

}
