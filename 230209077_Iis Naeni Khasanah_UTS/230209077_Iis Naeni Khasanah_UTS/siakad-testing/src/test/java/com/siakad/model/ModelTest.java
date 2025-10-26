package com.siakad.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test terpadu untuk seluruh class model:
 * 1. Student
 * 2. Course
 * 3. CourseGrade
 * 4. Enrollment
 *
 * Tujuan: memastikan seluruh getter, setter, dan logika sederhana
 * teruji dengan baik untuk mencapai coverage di atas 90%.
 */
public class ModelTest {

    private Student student;
    private Course course;
    private CourseGrade courseGrade;
    private Enrollment enrollment;

    @BeforeEach
    void setUp() {
        // Inisialisasi objek sebelum setiap pengujian
        student = new Student();
        course = new Course();
        courseGrade = new CourseGrade();
        enrollment = new Enrollment();
    }

    // ====================== 1. TEST UNTUK CLASS STUDENT ======================

    // 1.1 Uji konstruktor kosong Student
    @Test
    void testStudentDefaultConstructor() {
        assertNull(student.getStudentId());
        assertEquals(0, student.getSemester());
        assertEquals(0.0, student.getGpa());
        assertNull(student.getAcademicStatus());
    }

    // 1.2 Uji konstruktor berparameter Student
    @Test
    void testStudentParameterizedConstructor() {
        Student s = new Student("S01", "Rina", "rina@poltek.ac.id", "Teknik Komputer", 4, 3.75, "ACTIVE");
        assertEquals("S01", s.getStudentId());
        assertEquals("Rina", s.getName());
        assertEquals("rina@poltek.ac.id", s.getEmail());
        assertEquals("Teknik Komputer", s.getMajor());
        assertEquals(4, s.getSemester());
        assertEquals(3.75, s.getGpa());
        assertEquals("ACTIVE", s.getAcademicStatus());
    }

    // 1.3 Uji setter dan getter Student
    @Test
    void testStudentSettersAndGetters() {
        student.setStudentId("S02");
        student.setName("Budi");
        student.setEmail("budi@poltek.ac.id");
        student.setMajor("Sistem Informasi");
        student.setSemester(2);
        student.setGpa(3.0);
        student.setAcademicStatus("PROBATION");

        assertEquals("S02", student.getStudentId());
        assertEquals("Budi", student.getName());
        assertEquals("budi@poltek.ac.id", student.getEmail());
        assertEquals("Sistem Informasi", student.getMajor());
        assertEquals(2, student.getSemester());
        assertEquals(3.0, student.getGpa());
        assertEquals("PROBATION", student.getAcademicStatus());
    }

    // ====================== 2. TEST UNTUK CLASS COURSE ======================

    // 2.1 Uji konstruktor kosong Course
    @Test
    void testCourseDefaultConstructor() {
        assertNotNull(course.getPrerequisites());
        assertTrue(course.getPrerequisites().isEmpty());
    }

    // 2.2 Uji konstruktor berparameter Course
    @Test
    void testCourseParameterizedConstructor() {
        Course c = new Course("CS101", "Pemrograman Dasar", 3, 30, 10, "Dr. Andi");
        assertEquals("CS101", c.getCourseCode());
        assertEquals("Pemrograman Dasar", c.getCourseName());
        assertEquals(3, c.getCredits());
        assertEquals(30, c.getCapacity());
        assertEquals(10, c.getEnrolledCount());
        assertEquals("Dr. Andi", c.getLecturer());
        assertTrue(c.getPrerequisites().isEmpty());
    }

    // 2.3 Uji setter dan getter Course
    @Test
    void testCourseSettersAndGetters() {
        course.setCourseCode("CS102");
        course.setCourseName("Struktur Data");
        course.setCredits(4);
        course.setCapacity(40);
        course.setEnrolledCount(35);
        course.setLecturer("Ir. Rani");

        assertEquals("CS102", course.getCourseCode());
        assertEquals("Struktur Data", course.getCourseName());
        assertEquals(4, course.getCredits());
        assertEquals(40, course.getCapacity());
        assertEquals(35, course.getEnrolledCount());
        assertEquals("Ir. Rani", course.getLecturer());
    }

    // 2.4 Uji addPrerequisite normal
    @Test
    void testCourseAddPrerequisite_Normal() {
        course.addPrerequisite("CS001");
        assertEquals(1, course.getPrerequisites().size());
        assertTrue(course.getPrerequisites().contains("CS001"));
    }

    // 2.5 Uji addPrerequisite saat list null
    @Test
    void testCourseAddPrerequisite_WhenListNull() {
        course.setPrerequisites(null);
        course.addPrerequisite("CS002");
        assertNotNull(course.getPrerequisites());
        assertEquals(1, course.getPrerequisites().size());
    }

    // 2.6 Uji setPrerequisites mengganti list
    @Test
    void testCourseSetPrerequisites() {
        List<String> newList = new ArrayList<>();
        newList.add("CS003");
        newList.add("CS004");
        course.setPrerequisites(newList);
        assertEquals(2, course.getPrerequisites().size());
        assertEquals("CS003", course.getPrerequisites().get(0));
    }

    // ====================== 3. TEST UNTUK CLASS COURSEGRADE ======================

    // 3.1 Uji konstruktor kosong CourseGrade
    @Test
    void testCourseGradeDefaultConstructor() {
        assertNull(courseGrade.getCourseCode());
        assertEquals(0, courseGrade.getCredits());
        assertEquals(0.0, courseGrade.getGradePoint());
    }

    // 3.2 Uji konstruktor berparameter CourseGrade
    @Test
    void testCourseGradeParameterizedConstructor() {
        CourseGrade cg = new CourseGrade("CS101", 3, 4.0);
        assertEquals("CS101", cg.getCourseCode());
        assertEquals(3, cg.getCredits());
        assertEquals(4.0, cg.getGradePoint());
    }

    // 3.3 Uji setter dan getter CourseGrade
    @Test
    void testCourseGradeSettersAndGetters() {
        courseGrade.setCourseCode("CS202");
        courseGrade.setCredits(2);
        courseGrade.setGradePoint(3.5);
        assertEquals("CS202", courseGrade.getCourseCode());
        assertEquals(2, courseGrade.getCredits());
        assertEquals(3.5, courseGrade.getGradePoint());
    }

    // ====================== 4. TEST UNTUK CLASS ENROLLMENT ======================

    // 4.1 Uji konstruktor kosong Enrollment
    @Test
    void testEnrollmentDefaultConstructor() {
        assertNull(enrollment.getEnrollmentId());
        assertNull(enrollment.getStudentId());
        assertNull(enrollment.getCourseCode());
        assertNull(enrollment.getEnrollmentDate());
        assertNull(enrollment.getStatus());
    }

    // 4.2 Uji konstruktor berparameter Enrollment
    @Test
    void testEnrollmentParameterizedConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Enrollment e = new Enrollment("E01", "S01", "CS101", now, "PENDING");
        assertEquals("E01", e.getEnrollmentId());
        assertEquals("S01", e.getStudentId());
        assertEquals("CS101", e.getCourseCode());
        assertEquals(now, e.getEnrollmentDate());
        assertEquals("PENDING", e.getStatus());
    }

    // 4.3 Uji setter dan getter Enrollment
    @Test
    void testEnrollmentSettersAndGetters() {
        LocalDateTime time = LocalDateTime.of(2025, 10, 25, 12, 0);
        enrollment.setEnrollmentId("E02");
        enrollment.setStudentId("S02");
        enrollment.setCourseCode("CS202");
        enrollment.setEnrollmentDate(time);
        enrollment.setStatus("APPROVED");

        assertEquals("E02", enrollment.getEnrollmentId());
        assertEquals("S02", enrollment.getStudentId());
        assertEquals("CS202", enrollment.getCourseCode());
        assertEquals(time, enrollment.getEnrollmentDate());
        assertEquals("APPROVED", enrollment.getStatus());
    }
}
