package com.sam.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sam.user_service.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByName(String name);

    List<User> findAllByUserType(String userType);

    List<User> findAllByUserStatus(String userStatus);

    List<User> findAllByUserTypeAndUserStatus(String userType, String userStatus);
}
