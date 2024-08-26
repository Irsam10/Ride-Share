package com.sam.user_service.service;

import com.sam.user_service.dto.UserRequest;
import com.sam.user_service.model.User;
import com.sam.user_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final WKTWriter wktWriter = new WKTWriter();
    private final WKTReader wktReader = new WKTReader();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void createUser(UserRequest userRequest) {
        Point point = geometryFactory.createPoint(new Coordinate(userRequest.lat(),userRequest.lon()));

        User user = new User();
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());
        user.setAddress(userRequest.address());
        user.setPhone(userRequest.phone());
        user.setCurrentCoordinates(point);
        userRepository.save(user);
        log.info("User created: {}", user);
    }
}
