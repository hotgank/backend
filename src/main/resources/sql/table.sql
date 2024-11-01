DROP TABLE IF EXISTS u_users;
DROP TABLE IF EXISTS u_children;
DROP TABLE IF EXISTS u_parents_children;
DROP TABLE IF EXISTS u_wechat_users;
DROP TABLE IF EXISTS d_doctors;
DROP TABLE IF EXISTS d_doctors_children;
DROP TABLE IF EXISTS a_admins;
DROP TABLE IF EXISTS a_admin_logs;
DROP TABLE IF EXISTS o_units;
DROP TABLE IF EXISTS o_health_articles;
DROP TABLE IF EXISTS o_statistics;
DROP TABLE IF EXISTS r_reports;
DROP TABLE IF EXISTS c_consultations;
DROP TABLE IF EXISTS c_messages;
DROP TABLE IF EXISTS d_doctor_stats;

CREATE TABLE u_users (
                         user_id VARCHAR(40) PRIMARY KEY,
                         username VARCHAR(50),
                         password VARCHAR(50),
                         email VARCHAR(100),
                         phone CHAR(11),
                         registration_date DATETIME,
                         last_login DATETIME,
                         status ENUM('active', 'disabled'),
                         avatar_url TEXT
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
                                    FOREIGN KEY (child_id) REFERENCES u_children(child_id)
);

CREATE TABLE d_doctors (
                           doctor_id VARCHAR(40) PRIMARY KEY,
                           name VARCHAR(40),
                           password VARCHAR(50),
                           phone CHAR(11),
                           email VARCHAR(100),
                           age INT,
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

CREATE TABLE u_wechat_users (
                                user_id VARCHAR(40) PRIMARY KEY,
                                openid VARCHAR(50),
                                session_key VARCHAR(50),
                                unionid VARCHAR(50),
                                FOREIGN KEY (user_id) REFERENCES u_users(user_id)
);

CREATE TABLE d_doctors_children (
                                    relation_id INT AUTO_INCREMENT PRIMARY KEY,
                                    doctor_id VARCHAR(40),
                                    child_id VARCHAR(40),
                                    relation_type VARCHAR(20),
                                    created_at DATETIME,
                                    FOREIGN KEY (doctor_id) REFERENCES d_doctors(doctor_id),
                                    FOREIGN KEY (child_id) REFERENCES u_children(child_id)
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

CREATE TABLE o_units (
                         unit_name VARCHAR(50) PRIMARY KEY,
                         address VARCHAR(100),
                         admin_id VARCHAR(40),
                         unit_type VARCHAR(20),
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
                           result TEXT,
                           analyse TEXT,
                           comment TEXT,
                           doctor_id VARCHAR(40),
                           url TEXT,
                           FOREIGN KEY (child_id) REFERENCES u_children(child_id),
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

CREATE TABLE c_messages (
                            message_id INT AUTO_INCREMENT PRIMARY KEY,
                            consultation_id INT,
                            sender_type ENUM('doctor', 'user'),
                            message_text TEXT,
                            timestamp DATETIME,
                            message_type ENUM('text', 'image', 'video', 'file'),
                            url TEXT,
                            FOREIGN KEY (consultation_id) REFERENCES c_consultations(consultation_id)
);

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
-- 在表创建完成后，添加外键约束
ALTER TABLE a_admins
    ADD FOREIGN KEY (supervisor_id) REFERENCES a_admins(admin_id);