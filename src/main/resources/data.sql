DROP TABLE IF EXISTS favorite_resources;
DROP TABLE IF EXISTS favorites;
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

-- Clear courses table and reset auto-increment primary key
DELETE FROM courses;
ALTER TABLE courses AUTO_INCREMENT = 1;

-- Clear departments table and reset auto-increment primary key
DELETE FROM departments;
ALTER TABLE departments AUTO_INCREMENT = 1;

-- Clear users table and reset auto-increment primary key
DELETE FROM users;
ALTER TABLE users AUTO_INCREMENT = 1;

-- Insert test departments
INSERT INTO departments (name) VALUES 
('Computer Science Department'),
('Electronic Information Department'),
('Architecture Department'),
('Art Department');

-- Insert test courses
INSERT INTO courses (department_id, title, description, date) VALUES 
-- Computer Science Courses
(1, 'Data Structures and Algorithms', 'Fundamental data structures and algorithm design', '2024-03-15'),
(1, 'Database Systems', 'Database design and management principles', '2024-03-16'),
(1, 'Software Engineering', 'Software development methodologies and practices', '2024-03-17'),
(1, 'Artificial Intelligence', 'Introduction to AI and machine learning', '2024-03-18'),

-- Electronic Information Courses
(2, 'Digital Circuit Design', 'Digital logic and circuit implementation', '2024-03-19'),
(2, 'Signal Processing', 'Digital signal processing techniques', '2024-03-20'),
(2, 'Communication Systems', 'Modern communication system principles', '2024-03-21'),
(2, 'Embedded Systems', 'Design and development of embedded systems', '2024-03-22'),

-- Architecture Courses
(3, 'Architectural Design', 'Basic principles of architectural design', '2024-03-23'),
(3, 'Building Technology', 'Modern building materials and techniques', '2024-03-24'),
(3, 'Urban Planning', 'Urban development and planning strategies', '2024-03-25'),
(3, 'Sustainable Architecture', 'Green building and sustainable design', '2024-03-26'),

-- Art Courses
(4, 'Digital Art', 'Digital art creation and design', '2024-03-27'),
(4, 'Visual Communication', 'Principles of visual design and communication', '2024-03-28'),
(4, 'Art History', 'History of art and design movements', '2024-03-29'),
(4, 'Interactive Design', 'Interactive media and user experience design', '2024-03-30');

-- Insert test user
INSERT INTO users (username, password, role, phone) VALUES 
('testuser', 'password123', 'USER', '13800138000');

-- Insert test favorites
INSERT INTO favorites (id, user_id, name, description, is_public, resource_count) VALUES
('f1', 1, 'My Favorites', 'Personal favorite folder', true, 2),
('f2', 1, 'Work Files', 'Work related documents', false, 1);

-- Insert test favorite resources
INSERT INTO favorite_resources (id, favorite_id, title, file_type, size, upload_time) VALUES
('fr1', 'f1', 'Java Study Notes', 'PDF', 1024000, '2024-03-15 10:00:00'),
('fr2', 'f1', 'Spring Boot Tutorial', 'PDF', 2048000, '2024-03-15 11:00:00'),
('fr3', 'f2', 'Project Plan', 'DOCX', 512000, '2024-03-15 12:00:00');