package com.logger.log;

import com.logger.log.dto.LogDto;
import com.logger.log.dto.LogMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogConsumer {

    LogService logService;
    LogMapper logMapper;

    @RabbitListener(queues = {"${config.rabbitmq.logger-queue}"})
    public void storeLog(LogDto logDto) {
        logService.storeLog(logMapper.logDtoToLog(logDto));
    }

}
