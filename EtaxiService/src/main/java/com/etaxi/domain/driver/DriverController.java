package com.etaxi.domain.driver;

import com.etaxi.domain.driver.dto.DriverCreateRequest;
import com.etaxi.domain.driver.dto.DriverCreateResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DriverController {

    DriverService driverService;

    @PostMapping("/signup")
    ResponseEntity<DriverCreateResponse> signup(
            @Valid @RequestBody DriverCreateRequest driverRequest,
            Authentication authentication
    ) {
        return ResponseEntity.ok(driverService.createDriver(driverRequest, authentication));
    }

}
