CREATE TABLE IF NOT EXISTS `user_permission` (
    `user_id` INT,
    `permission_id` INT,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`, `permission_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`permission_id`) REFERENCES `permission`(`permission_id`) ON DELETE CASCADE
    );