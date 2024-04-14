CREATE TABLE IF NOT EXISTS `dept` (
    `dept_no` VARCHAR(20) NOT NULL primary key,
    `dname` VARCHAR(50) NOT NULL,
    `location` VARCHAR(40),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );
INSERT INTO DEPT(dept_no, dname, location, created_at, updated_at)
values(10, "監控技術部", "內湖總部", now(), now());

INSERT INTO DEPT(dept_no, dname, location, created_at, updated_at)
values(20, "人事", "信義", now(), now());

INSERT INTO DEPT(dept_no, dname, location, created_at, updated_at)
values(30, "業務部", "板橋", now(), now());

INSERT INTO DEPT(dept_no, dname, location, created_at, updated_at)
values(40, "總務部", "內湖總部", now(), now());

INSERT INTO DEPT(dept_no, dname, location, created_at, updated_at)
values(50, "財務部", "信義", now(), now());