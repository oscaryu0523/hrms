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

INSERT INTO EMP(ename, dept_no, job, email, phone_number, address, hiredate, sal, created_at, updated_at)
values("oscar", '10', "工程師", "oscar@gmail.com", '0912208671', "台北市內湖區堤頂大道二段187號", '2024-04-11', 70000, now(), now());

INSERT INTO EMP(ename, dept_no, job, email, phone_number, address, hiredate, sal, created_at, updated_at)
values("roger", '10', "資深工程師", "roger@gmail.com", '091224545', "台北市內湖區堤頂大道二段187號", '2024-03-02', 90000, now(), now());

INSERT INTO EMP(ename, dept_no, job, email, phone_number, address, hiredate, sal, created_at, updated_at)
values("lisa", '20', "資深管理師", "lisa@gmail.com", '091224546', "台北市信義區", '2024-02-02', 70000, now(), now());

INSERT INTO EMP(ename, dept_no, job, email, phone_number, address, hiredate, sal, created_at, updated_at)
values("willy", '20', "處長", "willy@gmail.com", '091224547', "台北市信義區", '2024-02-02', 110000, now(), now());

INSERT INTO EMP(ename, dept_no, job, email, phone_number, address, hiredate, sal, created_at, updated_at)
values("keiko", '30', "副總", "keiko@gmail.com", '091224548', "台北市板橋區", '2024-01-02', 110000, now(), now());