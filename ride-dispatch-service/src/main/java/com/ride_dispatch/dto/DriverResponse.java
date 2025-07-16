package com.ride_dispatch.dto;

public record DriverResponse(Long id, String name, String email, String password,
                          String address, String phone, Double lat, Double lon,String userType, String userStatus) {

}
