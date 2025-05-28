ALTER TABLE t_user
    ADD user_type CHAR NULL;

CREATE TABLE t_ride
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    start_location  POINT                  NULL,
    end_location    POINT                  NULL,
    status          CHAR                  NOT NULL,
    driver_id       BIGINT(20)                NULL,
    driver_name     VARCHAR(255)          NULL,
    passenger_id    BIGINT(20)                NULL,
    passenger_name  VARCHAR(255)          NULL,
    vehicle_type    VARCHAR(255)          NULL,
    vehicle_number  VARCHAR(255)          NULL,
    ride_type       VARCHAR(255)          NULL,
    fare            DOUBLE                NULL,
    ride_start_time time                  NULL,
    ride_end_time   time                  NULL,
    ride_date       date                  NULL,
    ride_distance   INT                   NULL,
    ride_rating     INT                   NULL,
    ride_feedback   VARCHAR(255)          NULL,
    CONSTRAINT pk_t_ride PRIMARY KEY (id)
);

ALTER TABLE t_ride
    ADD CONSTRAINT FK_DRIVER_ID FOREIGN KEY (driver_id) REFERENCES t_user (id);

ALTER TABLE t_ride
    ADD CONSTRAINT FK_PASSENGER_ID FOREIGN KEY (passenger_id) REFERENCES t_user (id);