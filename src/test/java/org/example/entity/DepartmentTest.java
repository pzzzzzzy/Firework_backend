package org.example.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentTest {

    @Test
    public void testDepartmentCreation() {
        Department department = new Department();
        assertNotNull(department);
    }

    @Test
    public void testDepartmentProperties() {
        Department department = new Department();
        
        // 設置屬性
        department.setId(1L);
        department.setName("Computer Science");
        List<Course> courses = new ArrayList<>();
        department.setCourses(courses);

        // 驗證屬性
        assertEquals(1L, department.getId());
        assertEquals("Computer Science", department.getName());
        assertEquals(courses, department.getCourses());
    }

    @Test
    public void testDepartmentEquality() {
        Department dept1 = new Department();
        dept1.setId(1L);
        dept1.setName("Computer Science");
        dept1.setCourses(new ArrayList<>());

        Department dept2 = new Department();
        dept2.setId(1L);
        dept2.setName("Computer Science");
        dept2.setCourses(new ArrayList<>());

        assertEquals(dept1, dept2);
        assertEquals(dept1.hashCode(), dept2.hashCode());
    }

    @Test
    public void testDepartmentToString() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Computer Science");
        department.setCourses(new ArrayList<>());

        String toString = department.toString();
        assertTrue(toString.contains("Computer Science"));
    }

    @Test
    public void testDepartmentWithCourses() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Computer Science");

        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setId(1L);
        course1.setTitle("Java Programming");
        course1.setDepartment(department);
        courses.add(course1);

        Course course2 = new Course();
        course2.setId(2L);
        course2.setTitle("Database Systems");
        course2.setDepartment(department);
        courses.add(course2);

        department.setCourses(courses);

        assertEquals(2, department.getCourses().size());
        assertEquals("Java Programming", department.getCourses().get(0).getTitle());
        assertEquals("Database Systems", department.getCourses().get(1).getTitle());
    }
} 