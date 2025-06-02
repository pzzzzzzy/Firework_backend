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

CREATE TABLE study_resources (
  id INT AUTO_INCREMENT PRIMARY KEY,
  course_id INT NOT NULL,
  title VARCHAR(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  type VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  size BIGINT NOT NULL,
  upload_time DATE NOT NULL,
  download_count INT DEFAULT 0,
  url VARCHAR(255) NOT NULL,
  FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- Clear study_resources table and reset auto-increment primary key
DELETE FROM study_resources;
ALTER TABLE study_resources AUTO_INCREMENT = 1;

-- Clear courses table and reset auto-increment primary key
DELETE FROM courses;
ALTER TABLE courses AUTO_INCREMENT = 85;

-- Clear departments table and reset auto-increment primary key
DELETE FROM departments;
ALTER TABLE departments AUTO_INCREMENT = 1;

-- Clear users table and reset auto-increment primary key (if any)
DELETE FROM users;
-- ALTER TABLE users AUTO_INCREMENT = 1;  -- Uncomment if there is an auto-increment primary key

-- Insert test departments
INSERT INTO departments (name) VALUES 
('Development'),
('Marketing'),
('HR');

-- Insert test courses
INSERT INTO courses (department_id, title, description, date) VALUES 
(1, 'Java Basics', 'Basic Java Programming Training', '2024-03-15'),
(1, 'Spring Boot Introduction', 'Spring Boot Framework Training', '2024-03-16'),
(2, 'Marketing Strategy', 'Basic Marketing Knowledge Training', '2024-03-17'),
(2, 'Brand Management', 'Brand Building and Management Training', '2024-03-18'),
(3, 'Recruitment Skills', 'Effective Recruitment Training', '2024-03-19'),
(3, 'Employee Relations', 'Employee Relations Management Training', '2024-03-20');

-- Insert test user (plain password)
INSERT INTO users (username, password, role, phone) VALUES 
('testuser', 'password123', 'USER', '13800138000');

-- Insert test study resources
INSERT INTO study_resources (course_id, title, type, size, upload_time, download_count, url) VALUES 
(85, 'Java Basics Slides.pdf', 'Slides', 1024576, '2024-03-15', 10, '/files/java-basics-slides.pdf'),
(85, 'Java Exercises.doc', 'Exercises', 512000, '2024-03-15', 5, '/files/java-exercises.doc'),
(85, 'Java Examples.zip', 'Examples', 2048000, '2024-03-15', 8, '/files/java-examples.zip'),
(86, 'Spring Boot Introduction.pdf', 'Slides', 1536000, '2024-03-16', 12, '/files/spring-boot-intro.pdf'),
(86, 'Spring Boot Homework.doc', 'Exercises', 768000, '2024-03-16', 6, '/files/spring-boot-homework.doc'),
(87, 'Marketing Strategy.pptx', 'Slides', 2560000, '2024-03-17', 15, '/files/marketing-strategy.pptx'),
(88, 'Brand Management Cases.pdf', 'Slides', 1843200, '2024-03-18', 9, '/files/brand-management-cases.pdf'),
(89, 'Recruitment Guide.pdf', 'Slides', 1228800, '2024-03-19', 7, '/files/recruitment-guide.pdf'),
(90, 'Employee Relations Manual.pdf', 'Slides', 1638400, '2024-03-20', 4, '/files/employee-relations.pdf');