package com.sam.user_service.service;

import com.sam.user_service.dto.LocationData;
import com.sam.user_service.dto.UserRequest;
import com.sam.user_service.model.User;
import com.sam.user_service.repository.UserRepository;
import com.sam.user_service.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void createUser(UserRequest userRequest) {
        try {
            Point point = geometryFactory.createPoint(new Coordinate(userRequest.lat(),userRequest.lon()));

            User user = new User();
            user.setName(userRequest.name());
            user.setEmail(userRequest.email());
            user.setPassword(userRequest.password());
            user.setAddress(userRequest.address());
            user.setPhone(userRequest.phone());
            user.setCurrentCoordinates(point);
            user.setUserType(userRequest.userType());
            user.setUserStatus(Constants.STATUS_NEW);// 'A' for active, 'I' for inactive, 'N' for new, 'B' for blocked
            userRepository.save(user);
            log.info("User created: {}", user);
        } catch (Exception e) {
            log.error("User could not be created: {}", userRequest);
            log.error(e.getMessage());
        }
    }

    public UserRequest getUser(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow();
            Point point = user.getCurrentCoordinates();
            return new UserRequest(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
                    user.getAddress(), user.getPhone(), point.getX(), point.getY(),user.getUserType(),user.getUserStatus());
        } catch (Exception e) {
            log.error("User could not be fetched: {}", id);
            log.error(e.getMessage());
        }
        return null;
    }

    public List<UserRequest> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return users.stream().map(user -> {
                Point point = user.getCurrentCoordinates();
                return new UserRequest(user.getId(), user.getName(),user.getEmail(),user.getPassword(),
                        user.getAddress(),user.getPhone(),point.getX(),point.getY(),user.getUserType(),user.getUserStatus());
            }).toList();
        } catch (Exception e) {
            log.error("Users could not be fetched");
            log.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        }
        catch (Exception e){
            log.error("User could not be deleted : {}", id);
            log.error(e.getMessage());
        }
    }

    public void updateUser(UserRequest userRequest) {
        try{
            Point point = geometryFactory.createPoint(new Coordinate(userRequest.lat(),userRequest.lon()));
            User user = userRepository.findByEmail(userRequest.email());
            user.setName(userRequest.name());
            user.setEmail(userRequest.email());
            user.setPassword(userRequest.password());
            user.setAddress(userRequest.address());
            user.setPhone(userRequest.phone());
            user.setCurrentCoordinates(point);
            user.setUserStatus(userRequest.userStatus());
            userRepository.save(user);
        }
        catch (Exception e){
            log.error("User could not be updated: {}", userRequest);
            log.error(e.getMessage());
        }
    }
    public void updateUserStatus(Long id, String status) {
        try {
            User user = userRepository.findById(id).orElseThrow();
            user.setUserStatus(status);
            userRepository.save(user);
            log.info("User status updated: {} to {}", id, status);
        } catch (Exception e) {
            log.error("User status could not be updated: {}", id);
            log.error(e.getMessage());
        }
    }

    public void updateUserCoordinates(Long id, Double lat, Double lon) {
        try {
            User user = userRepository.findById(id).orElseThrow();
            Point point = geometryFactory.createPoint(new Coordinate(lat, lon));
            user.setCurrentCoordinates(point);
            userRepository.save(user);
            log.info("User coordinates updated: {} to ({}, {})", id, lat, lon);
        } catch (Exception e) {
            log.error("User coordinates could not be updated: {}", id);
            log.error(e.getMessage());
        }
    }

    public UserRequest getUserByEmail(String email) {
        try {
            User user = userRepository.findByEmail(email);
            Point point = user.getCurrentCoordinates();
            return new UserRequest(user.getId(),user.getName(),user.getEmail(),user.getPassword()
                    ,user.getAddress(),user.getPhone(),point.getX(),point.getY(),user.getUserType(),user.getUserStatus());
        }
        catch (Exception e)
        {
            log.error("User could not be fetched: {}", email);
            log.error(e.getMessage());
        }
        return null;
    }

    public UserRequest getUserByName(String name) {
        try {
            User user = userRepository.findByName(name);
            Point point = user.getCurrentCoordinates();
            return new UserRequest(user.getId(),user.getName(),user.getEmail(),user.getPassword()
                        ,user.getAddress(),user.getPhone(),point.getX(),point.getY(),user.getUserType(),user.getUserStatus());
        }
        catch (Exception e)
        {
            log.error("User could not be fetched: {}", name);
            log.error(e.getMessage());
        }
        return null;
    }
    public List<UserRequest> getUsersByStatus(String status){
        try {
            List<User> users = userRepository.findAllByUserStatus(status);
            return users.stream().map(user -> {
                Point point = user.getCurrentCoordinates();
                return new UserRequest(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
                        user.getAddress(), user.getPhone(), point.getX(), point.getY(), user.getUserType(), user.getUserStatus());
            }).toList();
        } catch (Exception e) {
            log.error("Users could not be fetched by status: {}", status);
            log.error(e.getMessage());
        }
        return Collections.emptyList();
    }
    public List<UserRequest> getUsersByType(String type){
        try {
            List<User> users = userRepository.findAllByUserType(type);
            return users.stream().map(user -> {
                Point point = user.getCurrentCoordinates();
                return new UserRequest(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
                        user.getAddress(), user.getPhone(), point.getX(), point.getY(), user.getUserType(), user.getUserStatus());
            }).toList();
        } catch (Exception e) {
            log.error("Users could not be fetched by type: {}", type);
            log.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    public LocationData getLiveLocation(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow();
            Point point = user.getCurrentCoordinates();
            return new LocationData(user.getId(), point.getX(), point.getY());
        } catch (Exception e) {
            log.error("Live location could not be fetched for user: {}", id);
            log.error(e.getMessage());
        }
        return null;
    }

    public List<UserRequest> getUsersByTypeAndStatus(String userType, String userStatus) {
        try {
            List<User> users = userRepository.findAllByUserTypeAndUserStatus(userType, userStatus);
            return users.stream().map(user -> {
                Point point = user.getCurrentCoordinates();
                return new UserRequest(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
                        user.getAddress(), user.getPhone(), point.getX(), point.getY(), user.getUserType(), user.getUserStatus());
            }).toList();
        } catch (Exception e) {
            log.error("Users could not be fetched by type: {} and status: {}", userType, userStatus);
            log.error(e.getMessage());
        }
        return Collections.emptyList();
    }
}
