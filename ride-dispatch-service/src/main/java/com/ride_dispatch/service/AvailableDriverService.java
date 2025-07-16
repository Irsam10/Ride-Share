package com.ride_dispatch.service;

import com.ride_dispatch.utils.AvailableDriversCache;
import com.ride_dispatch.dto.DriverResponse;
import com.ride_dispatch.dto.DriverLocation;
import com.ride_dispatch.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AvailableDriverService {
// This service is responsible for fetching available drivers periodically
    private static Map<Long, DriverLocation> availableDriversMap = null;
    private static List<DriverResponse> availableDrivers = null;
    private final AvailableDriversCache availableDriversCache;
    private RestTemplate restTemplate;
    @Value("${app.driver-service.get-all-active-drivers-endpoint}")
    private String getAllActiveDriversEndpoint;
    public AvailableDriverService(RestTemplate restTemplate, AvailableDriversCache availableDriversCache) {
        this.restTemplate = restTemplate;
        this.availableDriversCache = availableDriversCache;
    }
    public void getAvailableDrivers() {
        availableDrivers =  List.of((DriverResponse) Objects.requireNonNull (restTemplate.exchange(
                getAllActiveDriversEndpoint+ "?userType={userType}&userStatus={userStatus}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {},
                Constants.DRIVER,
                Constants.ACTIVE
        ).getBody()));
        availableDriversCache.updateDrivers(availableDrivers);
    }

}
