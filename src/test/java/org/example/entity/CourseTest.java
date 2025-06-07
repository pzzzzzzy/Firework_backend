package org.example.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class CourseTest {

    @Test
    public void testCourseCreation() {
        Course course = new Course();
        assertNotNull(course);
    }

    @Test
    public void testCourseProperties() {
        Course course = new Course();
        Department department = new Department();
        department.setId(1L);
        department.setName("Computer Science");
        
        LocalDate date = LocalDate.now();
        
        // 設置屬性
        course.setId(1L);
        course.setDepartment(department);
        course.setTitle("Java Programming");
        course.setDescription("Learn Java programming language");
        course.setDate(date);

        // 驗證屬性
        assertEquals(1L, course.getId());
        assertEquals(department, course.getDepartment());
        assertEquals("Java Programming", course.getTitle());
        assertEquals("Learn Java programming language", course.getDescription());
        assertEquals(date, course.getDate());
    }

    @Test
    public void testCourseEquality() {
        Course course1 = new Course();
        Course course2 = new Course();
        
        Department department = new Department();
        department.setId(1L);
        department.setName("Computer Science");
        
        LocalDate date = LocalDate.now();
        
        course1.setId(1L);
        course1.setDepartment(department);
        course1.setTitle("Java Programming");
        course1.setDescription("Learn Java programming language");
        course1.setDate(date);

        course2.setId(1L);
        course2.setDepartment(department);
        course2.setTitle("Java Programming");
        course2.setDescription("Learn Java programming language");
        course2.setDate(date);

        assertEquals(course1, course2);
        assertEquals(course1.hashCode(), course2.hashCode());
    }

    @Test
    public void testCourseToString() {
        Course course = new Course();
        Department department = new Department();
        department.setId(1L);
        department.setName("Computer Science");
        
        course.setId(1L);
        course.setDepartment(department);
        course.setTitle("Java Programming");
        course.setDescription("Learn Java programming language");
        course.setDate(LocalDate.now());

        String toString = course.toString();
        assertTrue(toString.contains("Java Programming"));
        assertTrue(toString.contains("Learn Java programming language"));
    }
} 