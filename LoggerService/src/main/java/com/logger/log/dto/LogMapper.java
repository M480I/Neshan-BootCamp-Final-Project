package com.logger.log.dto;

import com.logger.log.Log;
import org.springframework.stereotype.Component;

@Component
public class LogMapper {

    public Log logDtoToLog(LogDto logDto) {
        return Log.builder()
                .message(logDto.message())
                .date(logDto.date())
                .build();
    }

}
