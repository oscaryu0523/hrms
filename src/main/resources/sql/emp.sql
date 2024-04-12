CREATE TABLE IF NOT EXISTS `emp` (
    `emp_no` INT AUTO_INCREMENT PRIMARY KEY,
    `ename` VARCHAR(20) NOT NULL,
    `dept_no` VARCHAR(20) NOT NULL,
    `job` VARCHAR(40),
    `email` VARCHAR(40) UNIQUE,
    `phone_number` VARCHAR(20),
    `address` VARCHAR(40),
    `hiredate` datetime DEFAULT NULL,
    `sal` decimal(7,2) DEFAULT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );