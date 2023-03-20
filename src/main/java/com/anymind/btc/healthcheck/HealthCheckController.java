package com.anymind.btc.healthcheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.anymind.btc.wallet.common.BaseAPIResponse;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HealthCheckController {

    private static final Logger logger = LoggerFactory.getLogger(HealthCheckController.class);

    /**
     * Returns a 200 response at the '/ledger' path. Check if service is running
     *
     * @return
     */
    @GetMapping(value = "/health")
    public ResponseEntity<BaseAPIResponse> getHealthCheck(HttpServletRequest servletRequest) {

        BaseAPIResponse apiResponse = new BaseAPIResponse("OK", "Ledger wallet is running.");
        logger.info("getHealthCheck: {}", apiResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}