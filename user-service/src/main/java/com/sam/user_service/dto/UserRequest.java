package com.sam.user_service.dto;

public record UserRequest(Long id, String name, String email, String password,
                          String address, String phone, Double lat, Double lon,Character userType) {

}
