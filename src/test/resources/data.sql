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
INSERT INTO study_resources (course_id, name, file_size, file_type, upload_time) 
VALUES 
    (1, 'Java Basic Guide.pdf', 1024576, 'application/pdf', '2024-03-15 10:00:00'),
    (1, 'Java Examples.zip', 2048576, 'application/zip', '2024-03-15 11:00:00'),
    (2, 'Python Tutorial.pdf', 1536576, 'application/pdf', '2024-03-16 10:00:00'),
    (2, 'Python Code Samples.zip', 3072576, 'application/zip', '2024-03-16 11:00:00'),
    (3, 'Marketing Slides.pptx', 5120576, 'application/vnd.ms-powerpoint', '2024-03-17 10:00:00'),
    (4, 'Brand Guidelines.pdf', 4096576, 'application/pdf', '2024-03-18 10:00:00'); 