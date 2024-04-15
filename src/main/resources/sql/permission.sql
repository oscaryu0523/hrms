CREATE TABLE IF NOT EXISTS `permission` (
    `permission_id` INT AUTO_INCREMENT PRIMARY KEY,
    `permission_name` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

INSERT INTO EMP(permission_name, created_at, updated_at)
values("員工管理", now(), now());

INSERT INTO EMP(permission_name, created_at, updated_at)
values("部門管理", now(), now());

INSERT INTO EMP(permission_name, created_at, updated_at)
values("權限管理", now(), now());

INSERT INTO EMP(permission_name, created_at, updated_at)
values("用戶權限管理", now(), now());



