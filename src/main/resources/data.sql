-- Clear existing data
DELETE FROM study_resources;
DELETE FROM favorite_resources;
DELETE FROM favorites;
DELETE FROM courses;
DELETE FROM departments;
DELETE FROM users;

-- Insert test departments
INSERT INTO departments (name) VALUES 
('Computer Science Department'),
('Electronic Information Department'),
('Architecture Department'),
('Art Department');

-- Insert test courses
INSERT INTO courses (department_id, title, description, date) VALUES 
(1, 'Java Basic Training', 'Java programming basic knowledge training', '2024-03-15'),
(1, 'Python Basic Training', 'Python programming basic knowledge training', '2024-03-16'),
(2, 'Marketing Strategy', 'Basic knowledge training of marketing', '2024-03-17'),
(2, 'Brand Management', 'Brand building and management training', '2024-03-18'),
(3, 'Recruitment Skills', 'Efficient recruitment skills training', '2024-03-19'),
(3, 'Employee Relationship Management', 'Employee relationship maintenance and management training', '2024-03-20'),
(2, 'Digital Circuit Design', 'Digital logic and circuit implementation', '2024-03-19'),
(2, 'Signal Processing', 'Digital signal processing techniques', '2024-03-20'),
(2, 'Communication Systems', 'Modern communication system principles', '2024-03-21'),
(2, 'Embedded Systems', 'Design and development of embedded systems', '2024-03-22'),
(3, 'Architectural Design', 'Basic principles of architectural design', '2024-03-23'),
(3, 'Building Technology', 'Modern building materials and techniques', '2024-03-24'),
(3, 'Urban Planning', 'Urban development and planning strategies', '2024-03-25'),
(3, 'Sustainable Architecture', 'Green building and sustainable design', '2024-03-26'),
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

-- Insert test study resources
INSERT INTO study_resources (course_id, name, file_size, file_type, upload_time) VALUES 
(1, 'Java Basic Guide.pdf', 1024576, 'application/pdf', '2024-03-15 10:00:00'),
(1, 'Java Examples.zip', 2048576, 'application/zip', '2024-03-15 11:00:00'),
(2, 'Python Tutorial.pdf', 1536576, 'application/pdf', '2024-03-16 10:00:00'),
(2, 'Python Code Samples.zip', 3072576, 'application/zip', '2024-03-16 11:00:00'),
(3, 'Marketing Slides.pptx', 5120576, 'application/vnd.ms-powerpoint', '2024-03-17 10:00:00'),
(4, 'Brand Guidelines.pdf', 4096576, 'application/pdf', '2024-03-18 10:00:00');
