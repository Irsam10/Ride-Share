package com.sam.user_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;


@Entity
@Table(name = "t_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String phone;
    private String userType; // 'D' for driver, 'P' for passenger
    private String userStatus; // 'A' for active, 'I' for inactive, 'N' for new, 'B' for blocked
    @Column(name = "curr_coords", columnDefinition = "POINT")
    private Point currentCoordinates;
}
