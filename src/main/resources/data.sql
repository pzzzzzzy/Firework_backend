-- 清空用户表
DELETE FROM users;

-- 插入测试用户（明文密码）
INSERT INTO users (username, password, role, phone) VALUES 
('testuser', 'password123', 'USER', '13800138000');

-- 清空课程表
DELETE FROM courses;

-- 清空部门表
DELETE FROM departments;

-- 插入测试部门
INSERT INTO departments (name) VALUES 
('研发部'),
('市场部'),
('人事部');

-- 插入测试课程
INSERT INTO courses (department_id, title, description, date) VALUES 
(1, 'Java基础培训', 'Java编程基础知识培训', '2024-03-15'),
(1, 'Spring Boot入门', 'Spring Boot框架使用培训', '2024-03-16'),
(2, '市场营销策略', '市场营销基础知识培训', '2024-03-17'),
(2, '品牌管理', '品牌建设与管理培训', '2024-03-18'),
(3, '招聘技巧', '高效招聘技巧培训', '2024-03-19'),
(3, '员工关系管理', '员工关系维护与管理培训', '2024-03-20');