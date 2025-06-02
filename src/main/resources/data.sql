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

-- Create study_resources table
CREATE TABLE study_resources (
  id INT AUTO_INCREMENT PRIMARY KEY,
  course_id INT,
  name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  file_size BIGINT NOT NULL,
  file_type VARCHAR(50) NOT NULL,
  upload_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- Clear courses table and reset auto-increment primary key
DELETE FROM courses;
ALTER TABLE courses AUTO_INCREMENT = 1;

-- Clear departments table and reset auto-increment primary key
DELETE FROM departments;
ALTER TABLE departments AUTO_INCREMENT = 1;

-- Clear users table and reset auto-increment primary key (if any)
DELETE FROM users;
-- ALTER TABLE users AUTO_INCREMENT = 1;  -- Uncomment if there is an auto-increment primary key

-- Insert test departments
INSERT INTO departments (name) VALUES 
('R&D Department'),
('Marketing Department'),
('HR Department');

-- Insert test courses
INSERT INTO courses (department_id, title, description, date) VALUES 
(1, 'Java Basic Training', 'Java programming basic knowledge training', '2024-03-15'),
(1, 'Python Basic Training', 'Python programming basic knowledge training', '2024-03-16'),
(2, 'Marketing Strategy', 'Basic knowledge training of marketing', '2024-03-17'),
(2, 'Brand Management', 'Brand building and management training', '2024-03-18'),
(3, 'Recruitment Skills', 'Efficient recruitment skills training', '2024-03-19'),
(3, 'Employee Relationship Management', 'Employee relationship maintenance and management training', '2024-03-20');

-- Insert test user (plain password)
INSERT INTO users (username, password, role, phone) VALUES 
('testuser', 'password123', 'USER', '13800138000');

-- Insert test study resources
INSERT INTO study_resources (course_id, name, file_size, file_type, upload_time) VALUES 
(1, 'Java Basic Guide.pdf', 1024576, 'application/pdf', '2024-03-15 10:00:00'),
(1, 'Java Examples.zip', 2048576, 'application/zip', '2024-03-15 11:00:00'),
(2, 'Python Tutorial.pdf', 1536576, 'application/pdf', '2024-03-16 10:00:00'),
(2, 'Python Code Samples.zip', 3072576, 'application/zip', '2024-03-16 11:00:00'),
(3, 'Marketing Slides.pptx', 5120576, 'application/vnd.openxmlformats-officedocument.presentationml.presentation', '2024-03-17 10:00:00'),
(4, 'Brand Guidelines.pdf', 4096576, 'application/pdf', '2024-03-18 10:00:00');