DROP TABLE IF EXISTS favorite_resources;
DROP TABLE IF EXISTS favorites;
DROP TABLE IF EXISTS study_resources;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS departments;
DROP TABLE IF EXISTS users;

CREATE TABLE departments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
);

CREATE TABLE courses (
  id INT AUTO_INCREMENT PRIMARY KEY,
  department_id INT,
  title VARCHAR(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  description VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  date DATE,
  FOREIGN KEY (department_id) REFERENCES departments(id)
);

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  password VARCHAR(128) NOT NULL,
  role VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  phone VARCHAR(16) NOT NULL
);

CREATE TABLE favorites (
    id VARCHAR(36) PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_public BOOLEAN DEFAULT false,
    resource_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE favorite_resources (
    id VARCHAR(36) PRIMARY KEY,
    favorite_id VARCHAR(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    file_type VARCHAR(50) NOT NULL,
    size BIGINT NOT NULL,
    upload_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (favorite_id) REFERENCES favorites(id)
);

CREATE TABLE study_resources (
  id INT AUTO_INCREMENT PRIMARY KEY,
  course_id INT,
  name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  file_size BIGINT NOT NULL,
  file_type VARCHAR(128) NOT NULL,
  upload_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (course_id) REFERENCES courses(id)
); 