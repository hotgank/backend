DROP DATABASE IF EXISTS DoctorChild;
CREATE DATABASE DoctorChild CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE DoctorChild;

DROP TABLE IF EXISTS u_users;
DROP TABLE IF EXISTS u_children;
DROP TABLE IF EXISTS u_parents_children;
DROP TABLE IF EXISTS u_wechat_users;
DROP TABLE IF EXISTS d_doctors;
DROP TABLE IF EXISTS d_doctors_users;
DROP TABLE IF EXISTS a_admins;
DROP TABLE IF EXISTS a_admin_logs;
DROP TABLE IF EXISTS o_schools;
DROP TABLE IF EXISTS o_hospitals;
DROP TABLE IF EXISTS o_health_articles;
DROP TABLE IF EXISTS o_statistics;
DROP TABLE IF EXISTS r_reports;
DROP TABLE IF EXISTS c_consultations;
DROP TABLE IF EXISTS c_messages;
DROP TABLE IF EXISTS d_doctor_stats;
DROP TABLE IF EXISTS a_license_check;

CREATE TABLE u_users (
    user_id VARCHAR(40) PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50),
    email VARCHAR(100),
    phone CHAR(11),
    registration_date DATETIME,
    last_login DATETIME,
    status ENUM('active', 'disabled'),
    avatar_url TEXT,
    openid VARCHAR(50),
    session_key VARCHAR(50)
);

CREATE TABLE u_children (
    child_id VARCHAR(40) PRIMARY KEY,
    name VARCHAR(40),
    school VARCHAR(50),
    gender VARCHAR(10),
    birthdate DATE,
    height INT,
    weight INT
);

CREATE TABLE u_parents_children (
    relation_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(40),
    child_id VARCHAR(40),
    relation_type VARCHAR(20),
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES u_users(user_id),
    FOREIGN KEY (child_id) REFERENCES u_children(child_id) ON DELETE CASCADE
);

CREATE TABLE d_doctors (
    doctor_id VARCHAR(40) PRIMARY KEY,
    name VARCHAR(40),
    username VARCHAR(40),
    password VARCHAR(50),
    phone CHAR(11),
    email VARCHAR(100),
    birthdate DATE,
    gender VARCHAR(10),
    position VARCHAR(40),
    workplace VARCHAR(100),
    qualification VARCHAR(100),
    experience VARCHAR(200),
    rating FLOAT,
    avatar_url TEXT,
    registration_date DATETIME,
    last_login DATETIME,
    status ENUM('active', 'disabled')
);


CREATE TABLE d_doctors_users (
    relation_id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id VARCHAR(40),
    user_id VARCHAR(40),
    relation_status VARCHAR(20),
    created_at DATETIME,
    FOREIGN KEY (doctor_id) REFERENCES d_doctors(doctor_id),
    FOREIGN KEY (user_id) REFERENCES u_users(user_id) ON DELETE CASCADE
);

CREATE TABLE a_admins (
    admin_id VARCHAR(40) PRIMARY KEY,
    admin_type ENUM('super', 'first', 'second'),
    supervisor_id VARCHAR(40),
    unit_name VARCHAR(50),
    username VARCHAR(50),
    password VARCHAR(50),
    email VARCHAR(100),
    phone CHAR(11),
    avatar_url TEXT,
    registration_date DATETIME,
    last_login DATETIME,
    status ENUM('active', 'disabled')
);

CREATE TABLE a_admin_logs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    admin_id VARCHAR(40),
    action_type VARCHAR(50),
    details TEXT,
    timestamp DATETIME,
    FOREIGN KEY (admin_id) REFERENCES a_admins(admin_id)
);

CREATE TABLE o_health_articles (
    article_id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id VARCHAR(40),
    title VARCHAR(100),
    content TEXT,
    publish_date DATETIME,
    type VARCHAR(20),
    status VARCHAR(20),
    FOREIGN KEY (doctor_id) REFERENCES d_doctors(doctor_id)
);

CREATE TABLE o_statistics (
    stat_id INT AUTO_INCREMENT PRIMARY KEY,
    stat_type VARCHAR(50),
    value INT,
    record_date DATE
);

CREATE TABLE r_reports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    child_id VARCHAR(40),
    created_at DATETIME,
    report_type VARCHAR(50),
    state VARCHAR(50),
    result TEXT,
    analyse TEXT,
    comment TEXT,
    doctor_id VARCHAR(40),
    url TEXT,
    FOREIGN KEY (child_id) REFERENCES u_children(child_id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES d_doctors(doctor_id)
);

CREATE TABLE c_consultations (
    consultation_id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id VARCHAR(40),
    user_id VARCHAR(40),
    consultation_start DATETIME,
    consultation_end DATETIME,
    rating INT,
    FOREIGN KEY (doctor_id) REFERENCES d_doctors(doctor_id),
    FOREIGN KEY (user_id) REFERENCES u_users(user_id)
);

-- c_messages 表，主键为 relation_id 和 message_seq 的组合
CREATE TABLE c_messages (
    relation_id INT,                        -- 引用 d_doctors_users 中的 relation_id
    message_seq INT,                        -- 消息序号，自动递增
    sender_type ENUM('doctor', 'user'),     -- 发送者类型，医生或用户
    message_text TEXT,                      -- 消息内容，适用于文本消息
    timestamp DATETIME,                     -- 消息时间戳
    message_type ENUM('text', 'image', 'video', 'file'),  -- 消息类型，支持文本、图片、视频、文件
    url TEXT,                               -- 如果是文件类型，存储文件路径或URL
    PRIMARY KEY (relation_id, message_seq), -- 组合主键：relation_id 和 message_seq
    FOREIGN KEY (relation_id) REFERENCES d_doctors_users(relation_id) ON DELETE CASCADE
);

-- 触发器：插入消息时，确保每个 relation_id 下的 message_seq 自动递增
DELIMITER $$

CREATE TRIGGER before_insert_c_messages
BEFORE INSERT ON c_messages
FOR EACH ROW
BEGIN
    -- 获取当前 relation_id 下最大 message_seq
    DECLARE max_seq INT;

    -- 查询当前会话下最大 message_seq
    SELECT COALESCE(MAX(message_seq), 0) INTO max_seq
    FROM c_messages
    WHERE relation_id = NEW.relation_id;

    -- 设置新插入消息的 message_seq 为当前最大值 + 1
    SET NEW.message_seq = max_seq + 1;
END $$

DELIMITER ;

CREATE TABLE d_doctor_stats (
    doctor_id VARCHAR(40),
    date DATE,
    rating FLOAT,
    patients INT,
    consultations INT,
    papers INT,
    PRIMARY KEY (doctor_id, date),
    FOREIGN KEY (doctor_id) REFERENCES d_doctors(doctor_id)
);

CREATE TABLE o_schools (
    school_name VARCHAR(50) PRIMARY KEY,
    address VARCHAR(100),
    admin_id VARCHAR(40)
);

CREATE TABLE o_hospitals (
    hospital_name VARCHAR(50) PRIMARY KEY,
    address VARCHAR(100),
    admin_id VARCHAR(40)
);

CREATE TABLE a_license_check (
    audit_id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id VARCHAR(40),
    admin_id VARCHAR(40),
    status VARCHAR(20),
    created_at DATETIME,
    url TEXT,
    updated_at DATETIME,
    comment TEXT,
    FOREIGN KEY (doctor_id) REFERENCES d_doctors(doctor_id),
    FOREIGN KEY (admin_id) REFERENCES a_admins(admin_id) ON DELETE CASCADE
);
-- 在表创建完成后，添加外键约束
ALTER TABLE a_admins
ADD FOREIGN KEY (supervisor_id) REFERENCES a_admins(admin_id);