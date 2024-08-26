CREATE TABLE `t_user` (
    `id` bigint(20) PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `address` VARCHAR(255),
    `phone` VARCHAR(255) NOT NULL,
    `curr_coords` POINT NOT NULL,
    SPATIAL INDEX `idx_curr_coords` (`curr_coords`)
);